package com.eazeup.eazehomework.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.eazeup.eazehomework.R;
import com.eazeup.eazehomework.api.model.GiphyResponse;
import com.eazeup.eazehomework.ui.GiphyDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Justin Schultz on 7/28/17.
 */

public class GiphyRecyclerViewAdapter extends RecyclerView.Adapter<GiphyViewHolder> {
    private List<GiphyResponse.Data> gifList;
    private Context context;

    public GiphyRecyclerViewAdapter(Context context, List<GiphyResponse.Data> gifList) {
        this.gifList = gifList;
        this.context = context;
    }

    public void updateItems(List<GiphyResponse.Data> gifList) {
        this.gifList = gifList;
    }

    @Override
    public GiphyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.giphy_list_item, null, false);

        return new GiphyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final GiphyViewHolder holder, final int position) {
        final GiphyResponse.Still still = gifList.get(position).images.still;
        final GiphyResponse.Original original = gifList.get(position).images.original;
        final String caption = gifList.get(position).caption != null ? gifList.get(position).caption : original.url;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GiphyDetailActivity.class);
                intent.putExtra(GiphyDetailActivity.GIF_CAPTION, caption);
                intent.putExtra(GiphyDetailActivity.GIF_URL, original.url);

                view.getContext().startActivity(intent);
            }
        });

        Picasso.with(context)
                .load(still.url)
                .into(holder.gifImage);

        // Pre-fetch the gif for the details screen
        Glide.with(context).download(original.url);
    }

    @Override
    public int getItemCount() {
        return this.gifList.size();
    }
}