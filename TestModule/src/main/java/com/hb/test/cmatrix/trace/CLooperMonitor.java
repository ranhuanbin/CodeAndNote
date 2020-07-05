package com.hb.test.cmatrix.trace;

import android.os.Build;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Printer;

import com.hb.test.cmatrix.CMatrixLogUtils;
import com.tencent.matrix.util.ReflectUtils;

import java.util.HashSet;

public class CLooperMonitor implements MessageQueue.IdleHandler {
    private static final long CHECK_TIME = 60 * 1000;
    public static final CLooperMonitor mainMonitor = new CLooperMonitor();
    private final HashSet<CLooperDispatchListener> listeners = new HashSet<>();
    private long lastCheckPrinterTime = 0;

    private LooperPrinter printer;
    private Looper looper;

    public CLooperMonitor() {
        CMatrixLogUtils.log(getClass(), "CLooperMonitor()");
        looper = Looper.getMainLooper();
        resetPrinter();
        addIdleHandler(looper);
    }

    private void resetPrinter() {
        Printer originPrinter = null;
        try {
            originPrinter = ReflectUtils.get(looper.getClass(), "mLogging", looper);
        } catch (Exception e) {
            CMatrixLogUtils.log(getClass(), "resetPrinter---exception: " + e.getMessage());
        }
        looper.setMessageLogging(printer = new LooperPrinter(originPrinter));
    }

    private synchronized void addIdleHandler(Looper looper) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            looper.getQueue().addIdleHandler(this);
        } else {
            try {
                MessageQueue queue = ReflectUtils.get(looper.getClass(), "mQueue", looper);
                queue.addIdleHandler(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static CLooperMonitor getInstance() {
        return mainMonitor;
    }

    public void addListener(CLooperDispatchListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(CLooperDispatchListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    @Override
    public boolean queueIdle() {
        if (SystemClock.uptimeMillis() - lastCheckPrinterTime >= CHECK_TIME) {
            resetPrinter();
            lastCheckPrinterTime = SystemClock.uptimeMillis();
        }
        return true;
    }

    private class LooperPrinter implements Printer {
        public Printer origin;
        boolean isHasChecked = false;
        boolean isValid = false;

        LooperPrinter(Printer printer) {
            this.origin = printer;
        }

        @Override
        public void println(String x) {
            if (origin != null) {
                origin.println(x);
            }
            if (!isHasChecked) {
                isValid = x.charAt(0) == '>' || x.charAt(0) == '<';
                isHasChecked = true;
            }
            if (isValid) {
                dispatch(x.charAt(0) == '>', x);
            }
        }
    }

    private void dispatch(boolean isBegin, String log) {
        for (CLooperDispatchListener listener : listeners) {
            if (isBegin) {
                if (!listener.isHasDispatchStart) {
                    listener.onDispatchStart(log);
                }
            } else {
                if (listener.isHasDispatchStart) {
                    listener.onDispatchEnd(log);
                }
            }
        }
    }
}
