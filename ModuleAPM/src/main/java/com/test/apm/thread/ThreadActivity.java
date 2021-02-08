package com.test.apm.thread;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.test.apm.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThreadActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        findViewById(R.id.getThreadCount).setOnClickListener(v -> {
            try {
                getThreadCount();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.getThreadInfo).setOnClickListener(v -> getThreadInfo());
        findViewById(R.id.createThread).setOnClickListener(v -> createThread());
    }

    private void createThread() {
        new Thread(() -> Log.v("AndroidTest", "create thread")).start();
    }

    private void getThreadCount() throws Exception {
        int i = android.os.Process.myPid();
        java.lang.Process p = Runtime.getRuntime().exec("cat /proc/" + i + "/status");
        InputStream is = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("Threads:")) {
                Log.v("AndroidTest", "line = " + line);
            }
        }
    }

    private void getThreadInfo() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while ((threadGroup.getParent()) != null) {
            // 返回此线程组的父线程组
            threadGroup = threadGroup.getParent();
        }
        Thread[] threads = new Thread[threadGroup.activeCount()];
        Log.v("AndroidTest", "threadCount = " + threadGroup.activeCount());
        // 把对此线程组中的所有活动子组的引用复制到指定数组中
        threadGroup.enumerate(threads);
        for (int i = 0; i < threads.length; i++) {
            Log.v("AndroidTest", "thread[" + i + "] = " + threads[i].getName());
        }
    }
}
