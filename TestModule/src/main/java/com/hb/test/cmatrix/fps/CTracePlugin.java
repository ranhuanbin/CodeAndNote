package com.hb.test.cmatrix.fps;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hb.test.cmatrix.CMatrixLogUtils;
import com.hb.test.cmatrix.trace.CUIThreadMonitor;
import com.tencent.matrix.resource.watcher.ActivityLifeCycleCallbacksAdapter;

import java.lang.ref.WeakReference;

public class CTracePlugin {
    private WeakReference<Activity> activityRef;
    private Application application;

    public CTracePlugin(Application app) {
        this.application = app;
        application.registerActivityLifecycleCallbacks(new ActivityLifeCycleCallbacksAdapter() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityRef = new WeakReference<>(activity);
            }
        });
    }

    public void init() {
        CUIThreadMonitor threadMonitor = CUIThreadMonitor.getInstance();
        threadMonitor.init();
        threadMonitor.addFpsListener((start, end, frameIntervalNanos) -> {
            if (activityRef.get() != null) {
                CMatrixLogUtils.log(CTracePlugin.class, "doFrame()---activity: " + activityRef.get() + ",start: " + start + ", end: " + end + ", cost: " + (end - start) + ", dropFrames: " + (int) (end - start) / frameIntervalNanos);
            }
        });
        threadMonitor.onStart();
    }
}
