package com.hb.test.cmatrix.trace;

import com.hb.test.cmatrix.CITracer;

public abstract class CTrace extends CLooperObserver implements CITracer {
    @Override
    public void onStartTrace() {
        onAlive();
    }

    @Override
    public void onCloseTrace() {
        onDead();
    }
    protected void onAlive() {

    }

    protected void onDead() {

    }
}
