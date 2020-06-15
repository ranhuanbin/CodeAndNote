package com.test.apm.customleak;

import android.app.Application;

import com.test.apm.customleak.watch.ActivityRefWatcher;
import com.test.apm.customleak.watch.FragmentRefWatcher;
import com.test.apm.customleak.watch.RefWatcher;
import com.test.utils.AppUtils;

import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 从LeakCanary拷贝出内存泄漏时生成堆内存文件的代码ß
 */
public class LeakCanaryManager {
    private static AtomicBoolean init = new AtomicBoolean(false);

    public void init(Application app) {
        if (init.get()) {
            return;
        }
        init.set(true);
        AppUtils.setContext(app);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        RefWatcher refWatcher = new RefWatcher(executorService, queue);
        ActivityRefWatcher.init(refWatcher, app);
        FragmentRefWatcher.init(refWatcher, app);
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
