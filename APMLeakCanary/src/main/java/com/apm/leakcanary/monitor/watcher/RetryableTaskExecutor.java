package com.apm.leakcanary.monitor.watcher;

import android.os.Handler;
import android.os.HandlerThread;

public class RetryableTaskExecutor {
    private Handler backgroundHandler;
    private long delayMillis;

    public interface RetryableTask {
        enum Status {
            DONE, RETRY
        }

        Status execute();
    }

    public RetryableTaskExecutor(long delayMillis, HandlerThread handlerThread) {
        backgroundHandler = new Handler(handlerThread.getLooper());
        this.delayMillis = delayMillis;
    }

    public void executeInBackground(RetryableTask task) {
        postToBackgroundWithDelay(task, 0);
    }

    private void postToBackgroundWithDelay(final RetryableTask task, final int failedAttempts) {
        backgroundHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RetryableTask.Status status = task.execute();
                if (status == RetryableTask.Status.RETRY) {
                    postToBackgroundWithDelay(task, failedAttempts + 1);
                }
            }
        }, delayMillis);
    }
}
