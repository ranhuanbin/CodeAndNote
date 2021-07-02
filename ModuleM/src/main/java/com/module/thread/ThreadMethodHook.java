package com.module.thread;//package com.module.mst.thread;//package com.test.apm.thread;
//
//import android.util.Log;
//
//import de.robv.android.xposed.XC_MethodHook;
//
//public class ThreadMethodHook extends XC_MethodHook {
//    @Override
//    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//        super.beforeHookedMethod(param);
//        Thread t = (Thread) param.thisObject;
//        Log.v("AndroidTest", "thread = " + t + ", threadId = " + t.getId() + ", started..");
//    }
//
//    @Override
//    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//        super.afterHookedMethod(param);
//        Thread t = (Thread) param.thisObject;
//        Log.v("AndroidTest", "thread:" + t + ", threadId = " + t.getId() + ", exit..");
//    }
//}