package com.eazeup.eazehomework.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model for a GIPHY image.
 */

public class Image implements Parcelable {

    @SerializedName("url") @Expose public String url;
    @SerializedName("width") @Expose public Integer width;
    @SerializedName("height") @Expose public Integer height;
    @SerializedName("size") @Expose public Integer size;
    @SerializedName("mp4") @Expose public String mp4;
    @SerializedName("mp4_size") @Expose public Integer mp4Size;
    @SerializedName("webp") @Expose public String webp;
    @SerializedName("webp_size") @Expose public Integer webpSize;

    protected Image(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        size = in.readInt();
        mp4 = in.readString();
        mp4Size = in.readInt();
        webp = in.readString();
        webpSize = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeInt(size);
        parcel.writeString(mp4);
        parcel.writeInt(mp4Size);
        parcel.writeString(webp);
        parcel.writeInt(webpSize);
    }
}
