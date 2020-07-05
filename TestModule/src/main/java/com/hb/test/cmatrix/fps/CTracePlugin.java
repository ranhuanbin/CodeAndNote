package com.hb.test.cmatrix.fps;

import android.app.Application;

import com.hb.test.cmatrix.trace.CTraceConfig;
import com.hb.test.cmatrix.trace.CUIThreadMonitor;

public class CTracePlugin {
    private Application application;
    private CTraceConfig config;

    public CTracePlugin(CTraceConfig config) {
        this.config = config;
    }

    public void init(Application app) {
        application = app;
    }

    public void start() {
        CUIThreadMonitor.getInstance().init(config);
        CUIThreadMonitor.getInstance().onStart();
    }
}
