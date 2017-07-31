package com.eazeup.eazehomework.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.eazeup.eazehomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Justin Schultz on 7/28/17.
 */

public class GiphyViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.giphy_still_image)
    public ImageView gifImage;

    public GiphyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}