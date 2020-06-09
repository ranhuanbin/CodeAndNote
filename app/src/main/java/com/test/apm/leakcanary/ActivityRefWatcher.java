package com.test.apm.leakcanary;

import com.test.apm.leak.watcher.HeapDumper;
import com.test.apm.leakcanary.iservice.GcTrigger;
import com.test.apm.leakcanary.privoder.LeakDefaultProvider;
import com.test.utils.AppUtils;
import com.test.utils.LogUtils;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;

import static com.test.apm.leak.watcher.HeapDumper.RETRY_LATER;


public class ActivityRefWatcher {
    private ExecutorService executorService;
    private final Set<String> retainedKeys;
    private ReferenceQueue<Object> queue;
    private GcTrigger gcTrigger;
    private HeapDumper heapDumper;

    public ActivityRefWatcher(ExecutorService executorService, ReferenceQueue<Object> queue) {
        this.executorService = executorService;
        retainedKeys = new CopyOnWriteArraySet<>();
        this.queue = queue;
        init();
    }

    public void watch(KeyedWeakReference reference) {
        retainedKeys.add(reference.key);
        LogUtils.leakLog(ActivityRefWatcher.class, "watch()-----reference: " + reference.toString());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ensureGone(reference);
            }
        });
    }

    private void ensureGone(KeyedWeakReference reference) {
        LogUtils.leakLog(ActivityRefWatcher.class, "ensureGone()-----reference: " + reference.toString());
        removeWeaklyReachableReferences();
        LogUtils.leakLog(ActivityRefWatcher.class, "ensureGone()-----1-----ref.get(): " + reference.get());
        if (gone(reference)) {//没有发生内存泄漏
            LogUtils.leakLog(ActivityRefWatcher.class, "没有内存泄漏-----2-----ret.get(): " + reference.get());
            return;
        }
        // runGc()并不一定会触发GC, 只是建议虚拟机进行gc操作, 所以这里加一个哨兵, 判断GC是否执行
        WeakReference<Object> objRef = new WeakReference<>(new Object());
        gcTrigger.runGc();
        if (objRef.get() != null) {
            LogUtils.leakLog(ActivityRefWatcher.class, "gc没有被触发");
            //说明并没有触发GC操作, 如果没有触发GC, 需要进行重试机制, 后续进行优化
            return;
        }
        // 哨兵被回收, 说明进行了gc操作
        removeWeaklyReachableReferences();
        if (!gone(reference)) {
            LogUtils.leakLog(ActivityRefWatcher.class, "发生了内存泄漏");
            long time = System.currentTimeMillis();
            // 对内存进行快照, 生成dump文件
            File heapDumpFile = heapDumper.dumpHeap();
            LogUtils.leakLog(ActivityRefWatcher.class, "发生了内存泄漏-----interval: " + (System.currentTimeMillis() - time) + ", heapDumpFile: " + heapDumpFile.getAbsolutePath());
            if (heapDumpFile != RETRY_LATER) {
                showToast();
            }
        } else {
            LogUtils.leakLog(ActivityRefWatcher.class, "没有发生内存泄漏-----2");
        }
    }

    private void showToast() {
        AppUtils.showShortToast("发生了内存泄漏");
    }

    private boolean gone(KeyedWeakReference reference) {
        return !retainedKeys.contains(reference.key);
    }

    private void removeWeaklyReachableReferences() {
        KeyedWeakReference ref;
        while ((ref = (KeyedWeakReference) queue.poll()) != null) {
            LogUtils.leakLog(ActivityRefWatcher.class, "removeWeaklyReachableReferences()-----ref: " + ref.toString() + "ref.get(): " + ref.get());
            retainedKeys.remove(ref.key);
        }
    }

    private void init() {
        gcTrigger = LeakDefaultProvider.defaultGcTrigger;
        heapDumper = new CustomHeapDumper();
    }
}
