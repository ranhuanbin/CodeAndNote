package com.hb.test.cmatrix.trace;

import android.os.SystemClock;
import android.view.Choreographer;

import com.hb.test.cmatrix.CMatrixLogUtils;
import com.hb.test.cmatrix.fps.FpsListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class CUIThreadMonitor implements Runnable {
    private static final String ADD_CALLBACK = "addCallbackLocked";
    public static final int CALLBACK_INPUT = 0;
    private volatile long token = 0;
    private boolean isBelongFrame = false;
    private FpsListener fpsListener;
    private static final CUIThreadMonitor instance = new CUIThreadMonitor();
    private Object[] callbackQueues;
    private Method addInputQueue;
    private long frameIntervalNanos = 16666666;
    private boolean callbackExist = false;

    public void init() {
        Choreographer choreographer = Choreographer.getInstance();
        callbackQueues = reflectObject(choreographer, "mCallbackQueues");
        addInputQueue = reflectChoreographerMethod(callbackQueues[CALLBACK_INPUT], long.class, Object.class, Object.class);
        long mFrameIntervalNanos = reflectObject(choreographer, "mFrameIntervalNanos");
        frameIntervalNanos = TimeUnit.MILLISECONDS.convert(mFrameIntervalNanos, TimeUnit.NANOSECONDS) + 1;
        CMatrixLogUtils.log(getClass(), "init()---mFrameIntervalNanos: " + mFrameIntervalNanos + ", frameIntervalNanos: " + frameIntervalNanos);
        CLooperMonitor.getInstance().addListener(new CLooperDispatchListener() {

            @Override
            public void dispatchStart() {
                super.dispatchStart();
                CUIThreadMonitor.this.dispatchBegin();
            }

            @Override
            public void dispatchEnd() {
                super.dispatchEnd();
                CUIThreadMonitor.this.dispatchEnd();
            }
        });
    }

    public void addFpsListener(FpsListener listener) {
        this.fpsListener = listener;
    }

    public void onStart() {
        CMatrixLogUtils.log(getClass(), "onStart()-----addFrameCallback");
        addFrameCallback();
    }

    @Override
    public void run() {
        doFrameBegin();
    }

    private void dispatchBegin() {
        CMatrixLogUtils.log(getClass(), "dispatchBegin()");
        token = SystemClock.uptimeMillis();
    }

    private void doFrameBegin() {
        CMatrixLogUtils.log(getClass(), "doFrameBegin()");
        this.isBelongFrame = true;
    }

    private void doFrameEnd() {
        callbackExist = false;
        addFrameCallback();
        this.isBelongFrame = false;
    }

    private void dispatchEnd() {
        CMatrixLogUtils.log(getClass(), "dispatchEnd()---isBelongFrame: " + isBelongFrame);
        if (isBelongFrame) {//属于帧刷新
            doFrameEnd();
            long start = token;
            long end = SystemClock.uptimeMillis();
            if (fpsListener != null) {
                fpsListener.doFrame(start, end, frameIntervalNanos);
            }
        }
    }

    private synchronized void addFrameCallback() {
        if (callbackExist) {
            return;
        }
        try {
            addInputQueue.invoke(callbackQueues[0], -1, this, null);
            callbackExist = true;
        } catch (Exception e) {
            CMatrixLogUtils.log(getClass(), "addFrameCallback()---exception: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T reflectObject(Object instance, String name) {
        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (Exception e) {
            CMatrixLogUtils.log(getClass(), "reflectObject()---exception: " + e.getMessage());
        }
        return null;
    }

    private Method reflectChoreographerMethod(Object instance, Class<?>... argTypes) {
        try {
            Method method = instance.getClass().getDeclaredMethod(CUIThreadMonitor.ADD_CALLBACK, argTypes);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            CMatrixLogUtils.log(getClass(), "reflectChoreographerMethod()---exception: " + e.getMessage());
        }
        return null;
    }

    public static CUIThreadMonitor getInstance() {
        return instance;
    }
}
