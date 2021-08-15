package com.test;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "com/test/MainActivity------->onCreate");
        setContentView(R.layout.activity_main);
        MainActivity.test(10, "11", 12, "13");
    }

    public static int test(int a, String b, int c, String d) {
        int z = 1;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        Thread thread = new Thread(runnable);
        thread.setName("original");
        thread.start();
        return 0;
    }
}