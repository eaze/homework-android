package com.eazeup.eazehomework.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Model for a GIPHY gif.
 */

public class Gif implements Parcelable {
    @SerializedName("type") @Expose public String type;
    @SerializedName("id") @Expose public String id;
    @SerializedName("slug") @Expose public String slug;
    @SerializedName("url") @Expose public String url;
    @SerializedName("bitly_url") @Expose public String bitlyUrl;
    @SerializedName("embed_url") @Expose public String embedUrl;
    @SerializedName("username") @Expose public String username;
    @SerializedName("source") @Expose public String source;
    @SerializedName("rating") @Expose public String rating;
    @SerializedName("content_url") @Expose public String contentUrl;
    @SerializedName("source_tld") @Expose public String sourceTld;
    @SerializedName("source_post_url") @Expose public String sourcePostUrl;
    @SerializedName("update_datetime") @Expose public String updateDatetime;
    @SerializedName("create_datetime") @Expose public String createDatetime;
    @SerializedName("import_datetime") @Expose public String importDatetime;
    @SerializedName("trending_datetime") @Expose public String trendingDatetime;
    @SerializedName("images") @Expose public Map<String, Image> images;

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(type);
        out.writeString(id);
        out.writeString(slug);
        out.writeString(url);
        out.writeString(bitlyUrl);
        out.writeString(embedUrl);
        out.writeString(username);
        out.writeString(rating);
        out.writeString(contentUrl);
        out.writeString(sourceTld);
        out.writeString(sourcePostUrl);
        out.writeString(updateDatetime);
        out.writeString(createDatetime);
        out.writeString(importDatetime);
        out.writeString(trendingDatetime);
        writeMap(out, images);
    }

    public static final Parcelable.Creator<Gif> CREATOR
            = new Parcelable.Creator<Gif>() {
        public Gif createFromParcel(Parcel in) {
            return new Gif(in);
        }

        public Gif[] newArray(int size) {
            return new Gif[size];
        }
    };

    private Gif(Parcel in) {
        type = in.readString();
        id = in.readString();
        slug = in.readString();
        url = in.readString();
        bitlyUrl = in.readString();
        embedUrl = in.readString();
        username = in.readString();
        rating = in.readString();
        contentUrl = in.readString();
        sourceTld = in.readString();
        sourcePostUrl = in.readString();
        updateDatetime = in.readString();
        createDatetime = in.readString();
        importDatetime = in.readString();
        trendingDatetime = in.readString();
        readMap(images, in);
    }

    private void writeMap(Parcel out, Map<String, Image> images) {
        out.writeInt(images.size());
        for(Map.Entry<String,Image> entry : images.entrySet()){
            out.writeString(entry.getKey());
            out.writeParcelable(entry.getValue(), 0);
        }
    }


    private void readMap(Map<String, Image> images, Parcel in) {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            images.put(in.readString(), (Image) in.readParcelable(Image.class.getClassLoader()));
        }
    }
}
