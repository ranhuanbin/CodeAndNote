package com.apm.leakcanary.monitor.watcher;

import android.app.Activity;
import android.app.Application;
import android.os.Debug;
import android.util.Log;

import com.apm.leakcanary.utils.LeakHandlerThread;
import com.apm.leakcanary.utils.ToastUtils;
import com.apm.leakcanary.model.DestroyedActivityInfo;
import com.apm.leakcanary.monitor.LeakDetectManager;
import com.apm.leakcanary.monitor.watcher.RetryableTaskExecutor.RetryableTask;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActivityRefWatcher {

    private static final ConcurrentLinkedQueue<DestroyedActivityInfo> destroyedActivityInfos = new ConcurrentLinkedQueue<>();
    private static RetryableTaskExecutor retryableTaskExecutor;

    public static void startWatch(Application application) {
        application.registerActivityLifecycleCallbacks(activityLifeCycleCallbacksAdapter);
        retryableTaskExecutor = new RetryableTaskExecutor(2000, LeakHandlerThread.getDefaultHandleThread());
        scheduleDetectProcedure();
    }

    // 开始检测生产者
    private static void scheduleDetectProcedure() {
        retryableTaskExecutor.executeInBackground(retryableTask);
    }

    // 对执行onDestroy的Activity进行相关数据配置
    private static void watch(Activity activity) {
        String activityName = activity.getClass().getName();
        UUID uuid = UUID.randomUUID();
        String key = "ActivityLeak" + activityName +
                "_" + Long.toHexString(uuid.getMostSignificantBits()) +
                Long.toHexString(uuid.getLeastSignificantBits());
        DestroyedActivityInfo destroyedActivityInfo = new DestroyedActivityInfo(key, activity, activityName);
        destroyedActivityInfos.add(destroyedActivityInfo);
    }

    private static ActivityLifeCycleCallbacksAdapter activityLifeCycleCallbacksAdapter = new ActivityLifeCycleCallbacksAdapter() {
        @Override
        public void onActivityDestroyed(Activity activity) {
            watch(activity);
        }
    };

    private static RetryableTask retryableTask = new RetryableTask() {
        @Override
        public Status execute() {
            if (destroyedActivityInfos.isEmpty()) {
                return Status.RETRY;
            }
            // 增加哨兵对象, 只要GC就会被回收掉
            WeakReference<Object> sentinelRef = new WeakReference<>(new Object());
            triggerGc();
            if (sentinelRef.get() != null) {
                // 如果不为空, 说明一定没有执行过GC
                return Status.RETRY;
            }
            Iterator<DestroyedActivityInfo> iterator = destroyedActivityInfos.iterator();
            while (iterator.hasNext()) {
                DestroyedActivityInfo destroyedActivityInfo = iterator.next();
                if (destroyedActivityInfo.activityRef.get() == null) {
                    // 已经被回收了, 为何要这样设计? Matrix回应是Activity可能被一个长生命周期的对象持有, 但是还是可以被回收
                    iterator.remove();
                    continue;
                }
                ++destroyedActivityInfo.detectedCound;
                if (destroyedActivityInfo.detectedCound < 3) {//重试三次
                    continue;
                }
                // 如果执行到这里, 说明当前Activity已经发生了泄漏, 采集当前堆内存快照, 并进行题型
                try {
                    ToastUtils.showShort(destroyedActivityInfo.activityName + "发生内存泄漏");
                    Debug.dumpHprofData(getDumpFileDirectory(destroyedActivityInfo.activityName));
                } catch (IOException e) {
                    Log.d("AndroidTest", "内存泄漏检测失败");
                }
            }
            return Status.RETRY;
        }
    };

    private static void triggerGc() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().runFinalization();
    }

    private static String getDumpFileDirectory(String activityName) {
        return LeakDetectManager.getContext().getFilesDir() + File.separator + "hprof" + File.separator + System.currentTimeMillis() + "_" + activityName + ".hprof";
    }
}
