package com.eazeup.eazehomework.api.model;

import com.squareup.moshi.Json;

/**
 * Created by Justin Schultz on 7/28/17.
 */

public class GiphyResponse {

    public Data[] data;
    // todo: Deal with pagination. Maybe endless scrolling?
    public Pagination pagination;

    public static class Data {
        public String caption;
        public Images images;
    }

    public static class Images {
        public Original original;
        @Json(name = "original_still")
        public Still still;
    }

    public static class Original {
        public String url;
    }

    public static class Still {
        public String url;
    }

    public static class Pagination {
        public int count;
        public int offset;
    }
}