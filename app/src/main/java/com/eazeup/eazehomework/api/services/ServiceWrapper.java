package com.eazeup.eazehomework.api.services;

/**
 * Created by Justin Schultz on 7/28/17.
 */

import com.eazeup.eazehomework.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ServiceWrapper {
    // TODO: Wrap Retrofit with RxJava to simplify callbacks and calls to REST services.
    // TODO: User-Agent, auth-token, retry mechanism, logging, custom deserialization.
    public static final <T> T wrapService(final Class<T> serviceClass) {
         OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", BuildConfig.GIPHY_API_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

         okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.GIPHY_API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpBuilder.build())
                .build();

        return retrofit.create(serviceClass);
    }
}
