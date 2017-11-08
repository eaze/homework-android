package com.eazeup.eazehomework;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.eazeup.eazehomework.fetch.GifSearchFetcher;
import com.eazeup.eazehomework.model.Gif;
import com.eazeup.eazehomework.model.GifSearchResult;
import com.eazeup.eazehomework.view.GifListAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String GIF_LIST = "gif_list";
    private static final String QUERY = "query";

    private final GifListAdapter mAdapter = new GifListAdapter();
    private final GifSearchFetcher mGifSearchFetcher = new GifSearchFetcher();
    private ClipboardManager mClipboardManager;
    private String mQuery = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        setupSearchBar();
        List<Gif> gifList = null;
        if (savedInstanceState != null) {
            mQuery = savedInstanceState.getString(QUERY, "");
            gifList = savedInstanceState.getParcelableArrayList(GIF_LIST);
        }
        setupList(gifList);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GIF_LIST, mAdapter.getGifList());
        outState.putString(QUERY, mQuery);
    }

    private void setupSearchBar() {
        EditText searchBar = (EditText) findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                getNewSearchResults(editable.toString());
            }
        });
    }

    private void setupList(@Nullable List<Gif> gifList) {
        UltimateRecyclerView recyclerView =
                (UltimateRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        if (gifList != null) {
            mAdapter.setGifList(gifList);
        }
        mAdapter.setListener(new GifListAdapter.ItemClickedListener() {
            @Override
            public void onItemClicked(int position, String url) {
                mClipboardManager.setPrimaryClip(
                        ClipData.newUri(getContentResolver(), url, Uri.parse(url)));
                Toast.makeText(MainActivity.this, "Copied " + url, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.enableDefaultSwipeRefresh(true);
        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewSearchResults(mQuery);
            }
        });
        recyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                loadMoreSearchResults();
            }
        });
        recyclerView.reenableLoadmore();
    }

    private void getNewSearchResults(String query) {
        mQuery = query;
        mGifSearchFetcher
                .search(query, 0)
                .subscribe(new Subscriber<GifSearchResult>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(GifSearchResult gifSearchResult) {
                        mAdapter.setGifList(gifSearchResult.data);
                    }
                });
    }

    private void loadMoreSearchResults() {
        mGifSearchFetcher
                .search(mQuery, mAdapter.getItemCount())
                .subscribe(new Subscriber<GifSearchResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(GifSearchResult gifSearchResult) {
                        mAdapter.addGifList(gifSearchResult.data);
                    }
                });
    }
}
