package com.apm.leakcanary.utils;

import android.os.HandlerThread;

public class LeakHandlerThread {
    private static HandlerThread defaultHandleThread;

    public static HandlerThread getDefaultHandleThread() {
        synchronized (LeakHandlerThread.class) {
            if (null == defaultHandleThread) {
                defaultHandleThread = new HandlerThread("LeakDetect");
                defaultHandleThread.start();
            }
            return defaultHandleThread;
        }
    }
}
