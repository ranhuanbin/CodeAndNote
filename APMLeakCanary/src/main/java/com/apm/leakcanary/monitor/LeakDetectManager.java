package com.apm.leakcanary.monitor;

import android.app.Application;
import android.content.Context;

import com.apm.leakcanary.monitor.watcher.ActivityRefWatcher;

public class LeakDetectManager {
    private static Application application;

    public static void init(Application app) {
        application = app;
        ActivityRefWatcher.startWatch(app);
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
