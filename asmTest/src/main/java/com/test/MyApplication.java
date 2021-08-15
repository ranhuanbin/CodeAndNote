package com.test;

import android.app.Application;
import android.app.Fragment;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyApplication extends Application {
    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    public List<FragmentActivity> activities = new ArrayList<>();
    public List<Fragment> fragments = new ArrayList<>();
    public static MyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        AppUtils.setContext(this);
//        LeakCanary.install(this);
//        LeakCanaryManager.getInstance().init(this);
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//                LogUtils.v(LeakMemoryTest.TAG, MyApplication.class, "onActivityDestroyed()");
//                LeakMemoryTest.getInstance().watch(activity);
//            }
//        });
//        LeakCanary.install(this);
//        LeakCanary.setConfig(new LeakCanary.Config());
    }

}
