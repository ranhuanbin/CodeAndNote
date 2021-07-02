package com.module.thread;

import android.os.Looper;
import android.util.Log;

public class ThreadTest {
    public static void test() {
//        JoinDemo thread = new JoinDemo();
//        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            Log.v("AndroidTest", "exception = " + e.getLocalizedMessage());
//        }
//        Log.v("AndroidTest", "主线程 test end");

//        try {
//            test1();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        new WaitNotifyDemo().startThreadA();

        Looper looper = Looper.myLooper();
        looper.prepare();
    }

    /**
     * 多条线程循环依次打出10次ABC
     */
    public static void test1() throws InterruptedException {
        JoinDemo t1 = new JoinDemo("t1", "A");
        JoinDemo t2 = new JoinDemo("t2", "B");
        JoinDemo t3 = new JoinDemo("t3", "C");
        for (int i = 0; i < 10; i++) {
            t1.start();
            t1.join();

            t2.start();
            t2.join();

            t3.start();
            t3.join();

            t1 = new JoinDemo("t1", "A");
            t2 = new JoinDemo("t2", "B");
            t3 = new JoinDemo("t3", "C");
        }
    }


    private static class JoinDemo extends Thread {
        public String num;
        public String curThread;

        public JoinDemo() {

        }

        public JoinDemo(String curThread, String num) {
            this.curThread = curThread;
            this.num = num;
        }

        @Override
        public void run() {
//            for (int i = 0; i < 10; i++) {
//                SystemClock.sleep(100);
//                Log.v("AndroidTest", "子线程 run i = " + i);
//            }
            Log.v("AndroidTest", "curThread = " + curThread + ", num = " + num);
        }
    }
}
