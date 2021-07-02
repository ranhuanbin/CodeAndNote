package com.lib.monitor.methodcost.normal;

import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;

public class MethodCostUtil {
    private static ConcurrentHashMap<String, Long> methodCostMap = new ConcurrentHashMap<>();

    public static synchronized void recordObjectMethodCostStart(int thresholdTime, String methodName) {
        if (!Thread.currentThread().getName().equals("main")) {
            return;
        }
        methodCostMap.put(methodName, System.currentTimeMillis());
    }


    public static synchronized void recordObjectMethodCostEnd(int thresholdTime, String methodName) {
        if (!Thread.currentThread().getName().equals("main")) {
            return;
        }
        if (!methodCostMap.containsKey(methodName)) {
            return;
        }

        long starttime = methodCostMap.get(methodName);
        long cost = System.currentTimeMillis() - starttime;
        if (cost < thresholdTime) {
            methodCostMap.remove(methodName);
            return;
        }
        Log.v("AndroidTest", methodName + " cost = " + cost + "ms");
    }
}
