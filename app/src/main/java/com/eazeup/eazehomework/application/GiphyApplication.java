package com.eazeup.eazehomework.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by jschultz on 3/9/18.
 */

public class GiphyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
