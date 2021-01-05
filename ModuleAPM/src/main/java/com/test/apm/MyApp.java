package com.test.apm;

import android.app.Application;

import com.test.apm.methodcost.MethodCostUtil;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void method() {
        MethodCostUtil.i("method_enter");
        String aaa = "aaa";
        String bbb = "bbb";
        MethodCostUtil.o("method_exit");
    }
}
