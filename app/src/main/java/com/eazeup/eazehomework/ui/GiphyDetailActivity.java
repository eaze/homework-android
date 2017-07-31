package com.eazeup.eazehomework.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.eazeup.eazehomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Justin Schultz on 7/30/17.
 */

public class GiphyDetailActivity extends AppCompatActivity {

    public static final String GIF_URL = "GIF_URL";
    public static final String GIF_CAPTION = "GIF_CAPTION";

    @BindView(R.id.giphy_image)
    GifImageView gifImageView;

    @BindView(R.id.fab)
    FloatingActionButton shareFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.initGifAndShareIntent();
    }

    private void initGifAndShareIntent() {
        final String gifUrl = getIntent().getStringExtra(GiphyDetailActivity.GIF_URL);
        final String gifCaption = getIntent().getStringExtra(GiphyDetailActivity.GIF_CAPTION);

        Glide.with(this).asGif().load(gifUrl).into(gifImageView);

        shareFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_gif_text, gifCaption, gifUrl));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
