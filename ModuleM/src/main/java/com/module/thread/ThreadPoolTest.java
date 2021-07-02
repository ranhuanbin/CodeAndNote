package com.module.thread;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    private static ExecutorService threadPool;

    public static void newScheduledThreadPool() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, Executors.defaultThreadFactory());
//        for (int i = 0; i < 10; i++) {
//            executor.schedule(new Task(i), 10 - i, TimeUnit.SECONDS);
//        }
        executor.scheduleAtFixedRate(new Task(1), 1000, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                throw new NullPointerException("抛出异常");
            }
        }, 5, 5, TimeUnit.SECONDS);
    }

    public static void synchronousQueue() {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        Integer poll = queue.poll();
        boolean offer1 = queue.offer(1);
        boolean offer2 = queue.offer(2);
        boolean offer3 = queue.offer(3);
        Log.v("AndroidTest", "poll = " + poll + ", offer1 = " + offer1 + ", offer2 = " + offer2 + ", offer3 = " + offer3 + ", size = " + queue.size());
    }

    public static void newThreadPool() {
//        threadPool = new ThreadPoolExecutor(1, 10, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        threadPool = new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS, new SynchronousQueue<>());
    }

    public static void newCachedThreadPool() {
        threadPool = Executors.newCachedThreadPool();
    }

    public static void submitTask() {
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Task(i));
        }
    }

    public static class Task implements Runnable {
        private int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            Log.v("AndroidTest", "taskId = " + taskId);
//            SystemClock.sleep(3000);
        }
    }
}
