package com.hb.test.base;

import android.app.Application;
import android.content.Intent;

import com.hb.test.cmatrix.trace.CTraceConfig;
import com.hb.test.cmatrix.trace.CTracePlugin;
import com.hb.test.matrix.config.DynamicConfigImplDemo;
import com.hb.test.matrix.listener.TestPluginListener;
import com.hb.test.utils.AppUtils;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.sqlitelint.SQLiteLint;
import com.tencent.sqlitelint.SQLiteLintPlugin;
import com.tencent.sqlitelint.config.SQLiteLintConfig;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setContext(this);
//        initMatrix();
        initCMatrix();
    }

    private void initCMatrix() {
        CTraceConfig config = new CTraceConfig.Builder().enableFPS(true).build();
        CTracePlugin plugin = new CTracePlugin(config);
        plugin.init(this);
        plugin.start();
    }

    private void initMatrix() {
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();

        Matrix.Builder builder = new Matrix.Builder(this);
        builder.patchListener(new TestPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .splashActivities("sample.tencent.matrix.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = new TracePlugin(traceConfig);
        builder.plugin(tracePlugin);

        if (matrixEnable) {

            //resource
            Intent intent = new Intent();
            ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.AUTO_DUMP;
            intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
            ResourceConfig resourceConfig = new ResourceConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .setAutoDumpHprofMode(mode)
                    .setDetectDebuger(true) //matrix test code
                    .setNotificationContentIntent(intent)
                    .build();
            builder.plugin(new ResourcePlugin(resourceConfig));
            ResourcePlugin.activityLeakFixer(this);

            //io
            IOCanaryPlugin ioCanaryPlugin = new IOCanaryPlugin(new IOConfig.Builder()
                    .dynamicConfig(dynamicConfig)
                    .build());
            builder.plugin(ioCanaryPlugin);


            // prevent api 19 UnsatisfiedLinkError
            //sqlite
            SQLiteLintConfig sqlLiteConfig;
            try {
                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
            } catch (Throwable t) {
                sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
            }
            builder.plugin(new SQLiteLintPlugin(sqlLiteConfig));
        }

        Matrix.init(builder.build());

        //start only startup tracer, close other tracer.
        tracePlugin.start();
    }
}
