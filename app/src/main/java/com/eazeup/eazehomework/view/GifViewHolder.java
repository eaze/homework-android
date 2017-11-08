package com.eazeup.eazehomework.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eazeup.eazehomework.R;
import com.eazeup.eazehomework.model.Gif;
import com.eazeup.eazehomework.model.Image;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;


public class GifViewHolder extends RecyclerView.ViewHolder {

    private static final String FIXED_WIDTH = "fixed_width";
    private final TextView title;
    private final TextView subtitle;
    private final SimpleDraweeView image;

    public GifViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        image = (SimpleDraweeView) itemView.findViewById(R.id.image);
        initImage();
    }

    private void initImage() {
        ProgressBarDrawable progress = new ProgressBarDrawable();
        progress.setColor(image.getResources().getColor(R.color.colorAccent));
        image.getHierarchy().setProgressBarImage(progress);
    }

    public void bind(Gif gif) {
        title.setText(gif.slug);
        subtitle.setText(gif.bitlyUrl);
        bindImage(gif);
    }

    private void bindImage(Gif gif) {
        Image gifImage = gif.images.get(FIXED_WIDTH);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(gifImage.url)
                .setAutoPlayAnimations(true)
                .build();
        image.setAspectRatio(gifImage.width / gifImage.height);
        image.setController(controller);
    }
}
