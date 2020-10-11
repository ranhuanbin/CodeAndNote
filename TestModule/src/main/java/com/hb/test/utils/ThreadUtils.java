package com.hb.test.utils;

public class ThreadUtils {
    public static String getCurrentThreadStackTrace() {
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int length = Math.min(stackTrace.length - 3, 15);
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(stackTrace[i]).append("\n");
        }
        return builder.toString();
    }
}
