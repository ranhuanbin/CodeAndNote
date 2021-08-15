package com.apm.leakcanary.model;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class DestroyedActivityInfo {
    public final String key;
    public final String activityName;
    public WeakReference<Activity> activityRef;
    public int detectedCound = 0;

    public DestroyedActivityInfo(String key, Activity activity, String activityName) {
        this.key = key;
        this.activityName = activityName;
        this.activityRef = new WeakReference<>(activity);
    }
}
