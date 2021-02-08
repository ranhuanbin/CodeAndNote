package com.test.apm;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.test.apm.methodcost.MethodCostUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        initThreadHook();
        test();
    }

//    private void initThreadHook() {
//        DexposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Thread thread = (Thread) param.thisObject;
//                Class<?> clazz = thread.getClass();
//                if (clazz != Thread.class) {
//                    Log.v("AndroidTest", "found class extend Thread:" + clazz);
//                    DexposedBridge.findAndHookMethod(clazz, "run", new ThreadMethodHook());
//                }
//                Log.v("AndroidTest", "ThreadName = " + thread.getName() + ", ThreadId = " + thread.getId() + ", class = " + thread.getClass() + " is created.");
//                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//                StringBuilder sb = new StringBuilder();
//                for (StackTraceElement element : stackTrace) {
//                    sb.append(element.toString()).append("\n");
//                }
//                Log.v("AndroidTest", "sb = " + sb.toString());
//            }
//        });
//        DexposedBridge.findAndHookMethod(Thread.class, "run", new ThreadMethodHook());
//    }

    public void method() {
        MethodCostUtil.i("method_enter");
        String aaa = "aaa";
        String bbb = "bbb";
        MethodCostUtil.o("method_exit");
    }

    private void test() {
//        int[] arr = new int[]{1, 2, 2, 1, 1, 3};
        int[] arr = new int[]{1, 2, 3, 4, 5, 6};
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            int tmp = res;
            res ^= arr[i];
            Log.v("AndroidTest", "arr[" + i + "] = " + arr[i] + ", tmp = " + tmp + ", res = " + res);
        }
        Log.v("AndroidTest", "res = " + res);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int value : arr) {
            map.put(value, map.getOrDefault(value, 0) + 1);
        }
        Set<Integer> set = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            set.add(entry.getValue());
        }
        return set.size() == map.size();
    }

}
