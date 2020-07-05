package com.hb.test.cmatrix.trace;

import android.os.SystemClock;
import android.view.Choreographer;

import com.hb.test.cmatrix.CBeatLifecycle;
import com.hb.test.cmatrix.CMatrixLogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;

public class CUIThreadMonitor implements CBeatLifecycle, Runnable {
    private static final String ADD_CALLBACK = "addCallbackLocked";
    public static final int CALLBACK_INPUT = 0;
    public static final int CALLBACK_ANIMATION = 1;
    public static final int CALLBACK_TRAVERSAL = 2;
    public static final int CALLBACK_LAST = CALLBACK_TRAVERSAL;

    private static final int DO_QUEUE_DEFAULT = 0;
    private static final int DO_QUEUE_BEGIN = 1;
    private static final int DO_QUEUE_END = 2;
    private volatile boolean isAlive = false;
    private long[] dispatchTimeMs = new long[4];
    private final HashSet<CLooperObserver> observers = new HashSet<>();
    private volatile long token = 0;
    private boolean isBelongFrame = false;
    private long[] queueCost = new long[CALLBACK_LAST + 1];

    private static final CUIThreadMonitor instance = new CUIThreadMonitor();
    private CTraceConfig config;
    private Choreographer choreographer;
    private Object callbackQueueLock;
    private Object[] callbackQueues;
    private Method addTraversalQueue;
    private Method addInputQueue;
    private Method addAnimationQueue;
    private long frameIntervalNanos = 16666666;
    private int[] queueStatus = new int[CALLBACK_LAST + 1];
    private boolean[] callbackExist = new boolean[CALLBACK_LAST + 1];

    public void init(CTraceConfig config) {
        this.config = config;
        choreographer = Choreographer.getInstance();
        callbackQueueLock = reflectObject(choreographer, "mLock");
        callbackQueues = reflectObject(choreographer, "mCallbackQueues");

        addInputQueue = reflectChoreographerMethod(callbackQueues[CALLBACK_INPUT], ADD_CALLBACK, long.class, Object.class, Object.class);
        addAnimationQueue = reflectChoreographerMethod(callbackQueues[CALLBACK_ANIMATION], ADD_CALLBACK, long.class, Object.class, Object.class);
        addTraversalQueue = reflectChoreographerMethod(callbackQueues[CALLBACK_TRAVERSAL], ADD_CALLBACK, long.class, Object.class, Object.class);
        frameIntervalNanos = reflectObject(choreographer, "mFrameIntervalNanos");

        CLooperMonitor.getInstance().addListener(new CLooperDispatchListener() {
            @Override
            public boolean isValid() {
                return true;
            }

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

    @Override
    public void onStart() {
        if (!isAlive) {
            synchronized (this) {
                callbackExist = new boolean[CALLBACK_LAST + 1];
            }
            queueStatus = new int[CALLBACK_LAST + 1];
            queueCost = new long[CALLBACK_LAST + 1];
            addFrameCallback(CALLBACK_INPUT, this);
        }
    }

    @Override
    public void onStop() {
        if (isAlive) {
            this.isAlive = false;
        }
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void run() {
        try {
            doFrameBegin(token);
            doQueueBegin(CALLBACK_INPUT);
            addFrameCallback(CALLBACK_ANIMATION, new Runnable() {
                @Override
                public void run() {
                    doQueueEnd(CALLBACK_INPUT);
                    doQueueBegin(CALLBACK_ANIMATION);
                }
            });
            addFrameCallback(CALLBACK_TRAVERSAL, new Runnable() {
                @Override
                public void run() {
                    doQueueEnd(CALLBACK_ANIMATION);
                    doQueueBegin(CALLBACK_TRAVERSAL);
                }
            });
        } catch (Exception e) {

        }
    }

    private void doQueueBegin(int type) {
        queueStatus[type] = DO_QUEUE_BEGIN;
        queueCost[type] = System.nanoTime();
    }

    private void dispatchBegin() {
        CMatrixLogUtils.log(getClass(), "dispatchBegin()");
        token = dispatchTimeMs[0] = SystemClock.uptimeMillis();
        dispatchTimeMs[2] = SystemClock.currentThreadTimeMillis();
        synchronized (observers) {
            for (CLooperObserver observer : observers) {
                if (!observer.isDispatchBegin()) {
                    observer.dispatchBegin(dispatchTimeMs[0], dispatchTimeMs[2], token);
                }
            }
        }
    }

    private void doFrameBegin(long token) {
        this.isBelongFrame = true;
    }

    private void doFrameEnd(long token) {
        doQueueEnd(CALLBACK_TRAVERSAL);
        queueStatus = new int[CALLBACK_LAST + 1];
        addFrameCallback(CALLBACK_INPUT, this);
        this.isBelongFrame = false;
    }

    private void doQueueEnd(int type) {
        queueStatus[type] = DO_QUEUE_END;
        queueCost[type] = System.nanoTime() - queueCost[type];
        synchronized (this) {
            callbackExist[type] = false;
        }
    }

    private void dispatchEnd() {
        CMatrixLogUtils.log(getClass(), "dispatchEnd()");
        if (isBelongFrame) {
            doFrameEnd(token);
        }

        long start = token;
        long end = SystemClock.uptimeMillis();

        synchronized (observers) {
            for (CLooperObserver observer : observers) {
                if (observer.isDispatchBegin()) {
                    observer.doFrame("", token, SystemClock.uptimeMillis(), isBelongFrame ? end - start : 0,
                            queueCost[CALLBACK_INPUT], queueCost[CALLBACK_ANIMATION], queueCost[CALLBACK_TRAVERSAL]);
                }
            }
        }
        dispatchTimeMs[3] = SystemClock.currentThreadTimeMillis();
        dispatchTimeMs[1] = SystemClock.uptimeMillis();
        synchronized (observers) {
            for (CLooperObserver observer : observers) {
                if (observer.isDispatchBegin()) {
                    observer.dispatchEnd(dispatchTimeMs[0], dispatchTimeMs[2], dispatchTimeMs[1], dispatchTimeMs[3], token, isBelongFrame);
                }
            }
        }
    }

    private synchronized void addFrameCallback(int type, Runnable callback) {
        if (callbackExist[type]) {
            return;
        }
        if (!isAlive && type == CALLBACK_INPUT) {
            return;
        }
        try {
            synchronized (callbackQueueLock) {
                Method method = null;
                switch (type) {
                    case CALLBACK_INPUT:
                        method = addInputQueue;
                        break;
                    case CALLBACK_ANIMATION:
                        method = addAnimationQueue;
                        break;
                    case CALLBACK_TRAVERSAL:
                        method = addTraversalQueue;
                        break;
                }
                if (null != method) {
                    method.invoke(callbackQueues[type], -1, callback, null);
                    callbackExist[type] = true;
                }
            }
        } catch (Exception e) {

        }
    }

    public long getFrameIntervalNanos() {
        return frameIntervalNanos;
    }

    public void addObserver(CLooperObserver observer) {
        if (!isAlive) {
            onStart();
        }
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(CLooperObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
            if (observers.isEmpty()) {
                onStop();
            }
        }
    }

    private <T> T reflectObject(Object instance, String name) {
        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method reflectChoreographerMethod(Object instance, String name, Class<?>... argTypes) {
        try {
            Method method = instance.getClass().getDeclaredMethod(name, argTypes);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
        }
        return null;
    }

    public static CUIThreadMonitor getInstance() {
        return instance;
    }
}
