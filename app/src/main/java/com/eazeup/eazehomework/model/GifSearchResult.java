package com.eazeup.eazehomework.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Gif search result.
 */

public class GifSearchResult {
    @SerializedName("data") @Expose public List<Gif> data;
    @SerializedName("meta") @Expose public Meta meta;
    @SerializedName("pagination") @Expose public Pagination pagination;

    public class Meta {
        @SerializedName("status") @Expose public Integer status;
        @SerializedName("msg") @Expose public String msg;
        @SerializedName("response_id") @Expose public String responseId;
    }

    public class Pagination {
        @SerializedName("total_count") @Expose public Integer totalCount;
        @SerializedName("count") @Expose public Integer count;
        @SerializedName("offset") @Expose public Integer offset;
    }
}
