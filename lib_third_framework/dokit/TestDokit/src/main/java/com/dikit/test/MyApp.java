package com.dikit.test;

import android.app.Application;

import com.didichuxing.doraemonkit.DoraemonKit;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DoraemonKit.install(this);
    }
}
