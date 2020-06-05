package com.test;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.internal.ActivityLifecycleCallbacksAdapter;
import com.test.apm.memory.LeakMemoryTest;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                LeakMemoryTest.getInstance().watch(activity);
            }
        });
    }
}
