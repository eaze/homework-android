package com.eazeup.eazehomework.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eazeup.eazehomework.R;
import com.eazeup.eazehomework.api.model.GiphyResponse;
import com.eazeup.eazehomework.api.services.ServiceWrapper;
import com.eazeup.eazehomework.api.services.giphy.GiphyService;
import com.eazeup.eazehomework.ui.adapter.GiphyRecyclerViewAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Justin Schultz on 7/28/17.
 */

public class MainActivity extends AppCompatActivity {

    public static final String GIPHY_RESPONSE = "GIPHY_RESPONSE";

    private StaggeredGridLayoutManager gridLayoutManager;
    private GiphyRecyclerViewAdapter rcAdapter;
    private GiphyResponse giphyResponse;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // todo: Put the number of columns in a resource with a screen-width qualifier for phones vs. tablets...
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        if (savedInstanceState == null) {
            this.getListItemData("nicholas cage");
        }
    }

    public void toggleProgress(boolean on) {
        if (on) {
            recyclerView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void getListItemData(@NonNull final String searchTerm) {
        toggleProgress(true);

        // Giphy's API is too fast, so I'm purposely delaying 1 second
        // to display the progress spinner for this demo.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GiphyService service = ServiceWrapper.wrapService(GiphyService.class);
                service.search(searchTerm).enqueue(new Callback<GiphyResponse>() {
                    @Override
                    public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                        giphyResponse = response.body();

                        if (rcAdapter == null) {
                            rcAdapter = new GiphyRecyclerViewAdapter(MainActivity.this, Arrays.asList(giphyResponse.data));
                            recyclerView.setAdapter(rcAdapter);
                        } else {
                            rcAdapter.updateItems(Arrays.asList(giphyResponse.data));
                            rcAdapter.notifyDataSetChanged();
                        }

                        toggleProgress(false);
                    }

                    @Override
                    public void onFailure(Call<GiphyResponse> call, Throwable t) {
                        toggleProgress(false);
                        Toast.makeText(getApplicationContext(), "A surprising new error occurred...", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // I was half-way through performing an appendectomy when I realized Wikipedia is full of
        // bad information. This is okay for a coding assignment, but terrible and ugly in the
        // real-world. Better separation of concerns is appropriate.
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                getListItemData(query);

                // Hide the keyboard after a search is performed. This should be a Util method or something.
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    // A quick-and-dirty way to save and restore the API response during teardown and creation cycles
    // Should use Parcelable or something like Realm in the real-world.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<GiphyResponse> jsonAdapter = moshi.adapter(GiphyResponse.class);
        String json = jsonAdapter.toJson(giphyResponse);

        savedInstanceState.putString(MainActivity.GIPHY_RESPONSE, json);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String json = savedInstanceState.getString(MainActivity.GIPHY_RESPONSE);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<GiphyResponse> jsonAdapter = moshi.adapter(GiphyResponse.class);

        try {
            this.giphyResponse = jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rcAdapter = new GiphyRecyclerViewAdapter(MainActivity.this, Arrays.asList(giphyResponse.data));
        recyclerView.setAdapter(rcAdapter);
    }
}
