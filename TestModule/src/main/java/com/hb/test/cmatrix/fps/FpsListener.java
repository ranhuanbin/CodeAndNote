package com.hb.test.cmatrix.fps;

public interface FpsListener {
    void doFrame(long start, long end, long frameIntervalNanos);
}
