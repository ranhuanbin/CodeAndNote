package com.module.mst.thread;

import android.util.Log;

public class NotifyWaitTest {
    static final Object object = new Object();
    static Thread a, b, c, d;

    public static void notifyWaitTest() {
        a = new Thread(() -> {
            try {
                synchronized (object) {
                    Log.v("AndroidTest", "A before wait " + System.nanoTime());
                    b.start();
                    Thread.sleep(1000);
                    object.wait();
                    Log.v("AndroidTest", "A after wait " + System.nanoTime());
                }
            } catch (Exception e) {
            }
        });
        a.start();
        b = new Thread(() -> {
            try {
                synchronized (object) {
                    Log.v("AndroidTest", "B before wait " + System.nanoTime());
                    c.start();
                    Thread.sleep(1000);
                    object.wait();
                    Log.v("AndroidTest", "B after wait " + System.nanoTime());
                }
            } catch (Exception e) {

            }
        });
        c = new Thread(() -> {
            try {
                synchronized (object) {
                    Log.v("AndroidTest", "C before wait " + System.nanoTime());
                    d.start();
                    Thread.sleep(1000);
                    object.wait();
                    Log.v("AndroidTest", "C after wait " + System.nanoTime());
                }
            } catch (Exception e) {

            }
        });

        d = new Thread(() -> {
            try {
                synchronized (object) {
                    Log.v("AndroidTest", "D before wait " + System.nanoTime());
                    object.notify();
                    object.notify();
                    object.notify();
//                    object.notifyAll();
                    Log.v("AndroidTest", "D after wait " + +System.nanoTime());
                }
            } catch (Exception e) {

            }
        });
    }

    private static boolean flag = true;
    private static Thread threadConsumer;
    private static Thread threadProducer;
    private static volatile int count = 0;
    private static final Object lock = new Object();

    // wait/notify机制实现
    public static void consumerProducerTest(boolean flag) {
        NotifyWaitTest.flag = flag;
        if (flag) {
            // 消费者线程
            threadConsumer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (NotifyWaitTest.flag) {
                        synchronized (lock) {
                            if (count == 0) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            } else {
                                Log.v("AndroidTest", "消费者消费数据 count = " + count);
                                try {
                                    Thread.sleep(1000);
                                    count = 0;
                                    lock.notify();
                                } catch (InterruptedException e) {
                                    Log.v("AndroidTest", "1 e = " + e);
                                    return;
                                }
                            }
                        }
                    }
                }
            });
            // 生成者线程
            threadProducer = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (NotifyWaitTest.flag) {
                        synchronized (lock) {
                            if (count != 0) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            } else {
                                Log.v("AndroidTest", "生产者生产数据 count = " + count);
                                try {
                                    Thread.sleep(1000);
                                    count = 1;
                                    lock.notify();
                                } catch (InterruptedException e) {
                                    Log.v("AndroidTest", "2 e = " + e);
                                    return;
                                }
                            }
                        }
                    }
                }
            });
            threadConsumer.start();
            threadProducer.start();
        }
    }
}
