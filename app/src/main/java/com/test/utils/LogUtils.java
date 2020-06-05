package com.test.utils;

import android.util.Log;

public class LogUtils {
    public static void v(Class<?> clazz, String msg) {
        Log.v(clazz.getSimpleName(), msg);
    }
}
