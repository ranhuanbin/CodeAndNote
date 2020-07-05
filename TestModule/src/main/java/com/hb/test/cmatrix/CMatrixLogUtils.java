package com.hb.test.cmatrix;

import android.util.Log;

public class CMatrixLogUtils {
    public static void log(Class clazz, String msg) {
        Log.v("CustomMatrix", clazz.getSimpleName() + "=====>" + msg);
    }
}
