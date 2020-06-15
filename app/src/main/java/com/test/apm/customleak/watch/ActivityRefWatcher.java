package com.test.apm.customleak.watch;

import android.app.Activity;
import android.app.Application;

import com.test.apm.customleak.ActivityLifecycleCallbackAdapter;
import com.test.apm.customleak.LeakCanaryManager;
import com.test.utils.LogUtils;


public class ActivityRefWatcher {

    public static void init(RefWatcher refWatcher, Application app) {
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbackAdapter() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtils.leakLog(LeakCanaryManager.class, "onDestroy()");
                refWatcher.watch(activity);
            }
        });
    }
}
