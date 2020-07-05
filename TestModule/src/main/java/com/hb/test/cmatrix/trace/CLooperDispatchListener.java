package com.hb.test.cmatrix.trace;

public class CLooperDispatchListener {
    boolean isHasDispatchStart = false;

    public boolean isValid() {
        return false;
    }

    public void dispatchStart() {

    }

    public void onDispatchStart(String x) {
        this.isHasDispatchStart = true;
        dispatchStart();
    }

    public void onDispatchEnd(String x) {
        this.isHasDispatchStart = false;
        dispatchEnd();
    }

    public void dispatchEnd() {

    }
}
