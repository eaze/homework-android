package com.eazeup.eazehomework.fetch;

import android.util.Log;

import com.eazeup.eazehomework.model.GifSearchResult;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Fetches Gifs from GIPHY.
 */
public class GifSearchFetcher {

    public static final int DEFAULT_LIMIT = 20;

    private static final String TAG = "GifSearchFetcher";
    private static final String API_KEY = "xZMaoReB8UNQTNjM6NTbmgMNGjAFXvgy";
    private OkHttpClient mHttpClient;

    interface GiphyApi {

        @GET("gifs/search")
        Observable<GifSearchResult> searchGifs(
                @Query("q") String query,
                @Query("limit") int limit,
                @Query("offset") int offset,
                @Query("api_key") String apiKey);
    }

    private GiphyApi mService;

    public GifSearchFetcher() {
        init();
    }

    private void init() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        mHttpClient = httpClientBuilder.build();
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("http://api.giphy.com/v1/")
                        .client(mHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        mService =
                retrofit.create(GiphyApi.class);
    }

    public Observable<GifSearchResult> search(String query, int offset) {
        mHttpClient.dispatcher().cancelAll();
        return mService.searchGifs(query, DEFAULT_LIMIT, offset, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
