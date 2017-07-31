package com.eazeup.eazehomework.api.services.giphy;

/**
 * Created by Justin Schultz on 7/28/17.
 */

import android.support.annotation.NonNull;

import com.eazeup.eazehomework.api.model.GiphyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyService {
    // todo: Paging query params, etc.
    @GET("search")
    Call<GiphyResponse> search(@Query("q") @NonNull String searchTerm);

    @GET("trending")
    Call<GiphyResponse> getTrendingGifs();
}
