package com.test.apm.methodcost;

import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;

public class MethodCostUtil {
    public static final String TAG = "SLOW_METHOD";
    public static ConcurrentHashMap<String, Long> methodCosts = new ConcurrentHashMap<>();

    public static void i(String methodDesc) {
        if (!Thread.currentThread().getName().equalsIgnoreCase("main")) {
            return;
        }
        methodCosts.put(methodDesc, System.currentTimeMillis());
    }

    public static void o(String methodDesc) {
        if (!Thread.currentThread().getName().equalsIgnoreCase("main")) {
            return;
        }
        if (TextUtils.isEmpty(methodDesc)) {
            return;
        }
        if (!methodCosts.containsKey(methodDesc)) {
            return;
        }
        long interval = System.currentTimeMillis() - methodCosts.get(methodDesc);
        if (interval >= 0) {
            Log.v(TAG, methodDesc + " cost " + interval + " ms");
        }
    }
}
