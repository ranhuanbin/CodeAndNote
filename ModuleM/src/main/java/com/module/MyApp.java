package com.module;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.github.anrwatchdog.ANRWatchDog;
import com.lib.monitor.largeimage.LargeImage;

public class MyApp extends Application {
    public static long attachTime;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        attachTime = System.currentTimeMillis();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("AndroidTest", "onCreate");
        Debug.startMethodTracing();
//        initMatrix();
//        initAnrWatchDog();
//        SystemClock.sleep(2000);
//        initEpicHook();


        LargeImage.getInstance().install(this)
                .setFileSizeThreshold(400)
                .setMemorySizeThreshold(100);

    }

//    private void initEpicHook() {
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

    private void initAnrWatchDog() {
        new ANRWatchDog().start();
    }

//    private void initMatrix() {
//        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
//        boolean matrixEnable = dynamicConfig.isMatrixEnable();
//        boolean fpsEnable = dynamicConfig.isFPSEnable();
//        boolean traceEnable = dynamicConfig.isTraceEnable();
//
//        Matrix.Builder builder = new Matrix.Builder(this);
//        builder.patchListener(new DefaultPluginListener(this));
//
//        //trace
//        TraceConfig traceConfig = new TraceConfig.Builder()
//                .dynamicConfig(dynamicConfig)
//                .enableFPS(fpsEnable)
//                .enableEvilMethodTrace(traceEnable)
//                .enableAnrTrace(traceEnable)
//                .enableStartup(traceEnable)
//                .splashActivities("sample.tencent.matrix.SplashActivity;")
//                .isDebug(true)
//                .isDevEnv(false)
//                .build();
//
//        TracePlugin tracePlugin = new TracePlugin(traceConfig);
//        builder.plugin(tracePlugin);
//
//        if (matrixEnable) {
//
//            //resource
//            Intent intent = new Intent();
//            ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.AUTO_DUMP;
//            intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
//            ResourceConfig resourceConfig = new ResourceConfig.Builder()
//                    .dynamicConfig(dynamicConfig)
//                    .setAutoDumpHprofMode(mode)
//                    .setDetectDebuger(true) //matrix test code
//                    .setNotificationContentIntent(intent)
//                    .build();
//            builder.plugin(new ResourcePlugin(resourceConfig));
//            ResourcePlugin.activityLeakFixer(this);
//
//            //io
//            IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
//                    .dynamicConfig(dynamicConfig)
//                    .build());
//            builder.plugin(ioCanaryPlugin);
//
//
//            // prevent api 19 UnsatisfiedLinkError
//            //sqlite
//            SQLiteLintConfig sqlLiteConfig;
//            try {
//                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
//            } catch (Throwable t) {
//                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
//            }
//            builder.plugin(new SQLiteLintPlugin(sqlLiteConfig));
//        }
//
//        Matrix.init(builder.build());
//
//        //start only startup tracer, close other tracer.
//        tracePlugin.start();
//    }

}
