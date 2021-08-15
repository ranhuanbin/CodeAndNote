package com.rhb.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountDownLatch latch = new CountDownLatch(5);
        latch.countDown();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() { }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() { }
        });
        executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() { return null; }
        });


    }

    @Override
    public void finish() {
        super.finish();
    }
}
