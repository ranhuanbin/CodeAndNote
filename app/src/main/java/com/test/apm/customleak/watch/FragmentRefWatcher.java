package com.test.apm.customleak.watch;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.test.apm.customleak.ActivityLifecycleCallbackAdapter;


public class FragmentRefWatcher {
    private static RefWatcher refWatcher;
    private static FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        @Override
        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
            refWatcher.watch(f);
        }
    };

    public static void init(RefWatcher refWatcher, Application app) {
        FragmentRefWatcher.refWatcher = refWatcher;
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbackAdapter() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                addWatch(activity);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void addWatch(Activity activity) {
        activity.getFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
    }
}
