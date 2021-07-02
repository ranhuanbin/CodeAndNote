package com.module.thread;

import android.os.SystemClock;
import android.util.Log;

public class WaitNotifyDemo {
    private final Object lock = new Object();

    private static void sleep(long time) {
        SystemClock.sleep(time);
    }

    private void log(String desc) {
        Log.v("AndroidTest", "thread = " + Thread.currentThread().getName() + ":" + desc);
    }

    public void startThreadA() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    log("get lock");
                    startThreadB();
                    log("start wait");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log("get lock after wait");
                    log("release lock");
                }
            }
        }, "thread-A").start();
    }

    private void startThreadB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    log("get lock");
                    startThreadC();
                    sleep(100);
                    log("start notify");
                    lock.notify();
                    log("release lock");
                }
            }
        }, "thread-B").start();
    }

    private void startThreadC() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    log("get lock");
                    log("release lock");
                }
            }
        }, "thread-c").start();
    }
}
