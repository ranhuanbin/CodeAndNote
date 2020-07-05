package com.hb.test.cmatrix.trace;

import android.app.Application;

import com.hb.test.cmatrix.fps.CFrameTracer;

public class CTracePlugin {
    private Application application;
    private CFrameTracer frameTracer;
    private CTraceConfig config;

    public CTracePlugin(CTraceConfig config) {
        this.config = config;
    }

    public void init(Application app) {
        application = app;
        frameTracer = new CFrameTracer(config);
    }

    public void start() {
        CUIThreadMonitor.getInstance().init(config);
        CUIThreadMonitor.getInstance().onStart();
        frameTracer.onStartTrace();
    }
}
