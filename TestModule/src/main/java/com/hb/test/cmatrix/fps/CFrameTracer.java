package com.hb.test.cmatrix.fps;

import com.hb.test.cmatrix.CMatrixLogUtils;
import com.hb.test.cmatrix.trace.CTrace;
import com.hb.test.cmatrix.trace.CTraceConfig;
import com.hb.test.cmatrix.trace.CUIThreadMonitor;

import java.util.concurrent.TimeUnit;

public class CFrameTracer extends CTrace {
    private CTraceConfig config;
    private long frameIntervalMs;

    public CFrameTracer(CTraceConfig config) {
        this.config = config;
        this.frameIntervalMs = TimeUnit.MILLISECONDS.convert(CUIThreadMonitor.getInstance().getFrameIntervalNanos(), TimeUnit.NANOSECONDS) + 1;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    protected void onAlive() {
        super.onAlive();
        CUIThreadMonitor.getInstance().addObserver(this);
    }

    @Override
    public void doFrame(String focusedActivityName, long start, long end, long frameCostMs, long inputCostNs, long animationCostNs, long traversalCostNs) {
        long taskCostMs = end - start;
        int dropFrame = (int) (taskCostMs / frameIntervalMs);
        CMatrixLogUtils.log(getClass(), "doFrame()---taskCostMs: " + taskCostMs + ", dropFrame: " + dropFrame);
    }
}
