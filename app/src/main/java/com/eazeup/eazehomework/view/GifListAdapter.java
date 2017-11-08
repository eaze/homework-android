package com.eazeup.eazehomework.view;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazeup.eazehomework.R;
import com.eazeup.eazehomework.model.Gif;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GifListAdapter extends UltimateViewAdapter<GifViewHolder> {

    public interface ItemClickedListener {
        void onItemClicked(final int position, String url);
    }
    private ArrayList<Gif> gifList = new ArrayList<>();
    private ItemClickedListener listener;

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.gif_item, parent, false);
        return new GifViewHolder(v);
    }

    @Override
    public GifViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public GifViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.gif_item, parent, false);
        return new GifViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, final int position) {
        final Gif gif = gifList.get(position);
        holder.bind(gif);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClicked(position, gif.bitlyUrl);
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return gifList.size();
    }

    @Override
    public int getAdapterItemCount() {
        return 0;
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    public ArrayList<Gif> getGifList() {
        ArrayList copy = new ArrayList();
        copy.addAll(gifList);
        return copy;
    }

    public void setGifList(List<Gif> gifList) {
        this.gifList.clear();
        this.gifList.addAll(gifList);
        notifyDataSetChanged();
    }

    public void addGifList(List<Gif> gifList) {
        int size = this.gifList.size();
        this.gifList.addAll(gifList);
        notifyItemRangeInserted(size, size + gifList.size());
    }

    public void setListener(ItemClickedListener listener) {
        this.listener = listener;
    }
}
