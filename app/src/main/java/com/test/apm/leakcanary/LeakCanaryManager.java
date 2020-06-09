package com.test.apm.leakcanary;

import android.app.Activity;
import android.app.Application;

import com.test.utils.AppUtils;
import com.test.utils.LogUtils;

import java.lang.ref.ReferenceQueue;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 从LeakCanary拷贝出内存泄漏时生成堆内存文件的代码ß
 */
public class LeakCanaryManager {
    private ActivityRefWatcher activityRefWatcher;
    private ExecutorService executorService;
    private ReferenceQueue<Object> queue;
    private static AtomicBoolean init = new AtomicBoolean(false);

    public void init(Application app) {
        if (init.get()) {
            return;
        }
        init.set(true);
        AppUtils.setContext(app);
        executorService = Executors.newSingleThreadExecutor();
        queue = new ReferenceQueue<>();
        activityRefWatcher = new ActivityRefWatcher(executorService, queue);
        app.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbackAdapter() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtils.leakLog(LeakCanaryManager.class, "onDestroy()");
                watch(new KeyedWeakReference(activity, UUID.randomUUID().toString(), "", queue));
            }
        });
        Thread
    }

    private void watch(KeyedWeakReference reference) {
        AppUtils.getMainHandler().postDelayed(() -> activityRefWatcher.watch(reference), 100);
    }

    private LeakCanaryManager() {

    }


    private static volatile LeakCanaryManager instance;

    public static LeakCanaryManager getInstance() {
        if (instance == null) {
            synchronized (LeakCanaryManager.class) {
                if (instance == null) {
                    instance = new LeakCanaryManager();
                }
            }
        }
        return instance;
    }
}
