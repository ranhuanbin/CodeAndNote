package com.test.apm.memory;

import android.os.SystemClock;

import com.squareup.leakcanary.GcTrigger;
import com.test.utils.LogUtils;

import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.NANOSECONDS;


public class LeakMemoryTest {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ReferenceQueue<Object> queue;
    private static volatile LeakMemoryTest instance;
    private final Set<String> retainedKeys;
    private final GcTrigger gcTrigger;

    private LeakMemoryTest() {
        queue = new ReferenceQueue<>();
        retainedKeys = new CopyOnWriteArraySet<>();
        gcTrigger = new GcTrigger() {
            @Override
            public void runGc() {
                Runtime.getRuntime().gc();
                enqueueReferences();
                System.runFinalization();
            }
        };
    }

    public void watch(Object watchedReference) {
        long watchStartNanoTime = System.nanoTime();
        String key = UUID.randomUUID().toString();
        retainedKeys.add(key);
        KeyedWeakReference reference = new KeyedWeakReference(watchedReference, key, queue);
        ensureGoneAsync(watchStartNanoTime, reference);
    }

    private void ensureGoneAsync(final long watchStartNanoTime, final KeyedWeakReference reference) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ensureGone(reference, watchStartNanoTime);
            }
        });
    }

    public void ensureGone(final KeyedWeakReference reference, final long watchStartNanoTime) {
        long gcStartNanoTime = System.nanoTime();
        long watchDurationMs = NANOSECONDS.toMillis(gcStartNanoTime - watchStartNanoTime);
        removeWeaklyReachableReferences();
        if (gone(reference)) {
            return;
        }
        gcTrigger.runGc();
        removeWeaklyReachableReferences();
        if (!gone(reference)) {
            long startDumpHeap = System.nanoTime();
            long gcDurationMs = NANOSECONDS.toMillis(startDumpHeap - gcStartNanoTime);
            long heapDumpDurationMs = NANOSECONDS.toMillis(System.nanoTime() - startDumpHeap);
            LogUtils.v(LeakMemoryTest.class, "startDumpHeap: " + startDumpHeap);
            LogUtils.v(LeakMemoryTest.class, "gcDurationMs: " + gcDurationMs);
            LogUtils.v(LeakMemoryTest.class, "heapDumpDurationMs: " + heapDumpDurationMs);
        }
    }

    private boolean gone(KeyedWeakReference reference) {
        return !retainedKeys.contains(reference.key);
    }

    private void removeWeaklyReachableReferences() {
        // WeakReferences are enqueued as soon as the object to which they point to becomes weakly
        // reachable. This is before finalization or garbage collection has actually happened.
        KeyedWeakReference ref;
        while ((ref = (KeyedWeakReference) queue.poll()) != null) {
            retainedKeys.remove(ref.key);
        }
    }

    private void enqueueReferences() {
        SystemClock.sleep(100);
    }

    public static LeakMemoryTest getInstance() {
        if (instance == null) {
            synchronized (LeakMemoryTest.class) {
                if (instance == null) {
                    instance = new LeakMemoryTest();
                }
            }
        }
        return instance;
    }

}
