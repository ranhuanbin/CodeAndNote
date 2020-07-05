package com.hb.test.cmatrix.trace;

public abstract class CLooperObserver {
    private boolean isDispatchBegin = false;

    public void dispatchBegin(long beginMs, long cpuBeginMs, long token) {
        isDispatchBegin = true;
    }

    public void doFrame(String focusedActivityName, long start, long end, long frameCostMs, long inputCostNs, long animationCostNs, long traversalCostNs) {

    }

    public void dispatchEnd(long beginMs, long cpuBeginMs, long endMs, long cpuEndMs, long token, boolean isBelongFrame) {
        isDispatchBegin = false;
    }

    public boolean isDispatchBegin() {
        return isDispatchBegin;
    }
}
