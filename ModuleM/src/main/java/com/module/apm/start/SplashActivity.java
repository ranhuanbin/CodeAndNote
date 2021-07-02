package com.module.apm.start;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.module.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SplashActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        Future<?> submit = threadPool.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        try {
            Object o = submit.get();
        } catch (ExecutionException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.submit(new Callable<String>() {
            @Override
            public String call() {
                return null;
            }
        });
    }
}
