package com.apm.leakcanary.utils;

import android.widget.Toast;

import com.apm.leakcanary.monitor.LeakDetectManager;

public class ToastUtils {
    private static final Toast toast = new Toast(LeakDetectManager.getContext());

    public static void showShort(String msg) {
        toast.setText(msg);
        toast.show();
    }
}
