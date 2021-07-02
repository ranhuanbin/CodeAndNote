package com.module.thread

import android.util.Log
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

object CountDownLatchTest {
    fun test() {
        val latch = CountDownLatch(2)
        val t1: Thread = object : Thread() {
            override fun run() {
                super.run()
                Log.v("AndroidTest", "t1 执行 开始")
                sleep(1000)
                latch.await()
                Log.v("AndroidTest", "t1 执行 结束")
            }
        }
        val t2: Thread = object : Thread() {
            override fun run() {
                super.run()
                Log.v("AndroidTest", "t2 执行 开始")
                sleep(2000)
                latch.await()
                Log.v("AndroidTest", "t2 执行 结束")
            }
        }
        val t3: Thread = object : Thread() {
            override fun run() {
                super.run()
                Log.v("AndroidTest", "t3 执行 开始")
                sleep(3000)
                latch.await()
                Log.v("AndroidTest", "t3 执行 结束")
            }
        }
        val es1 = Executors.newSingleThreadExecutor()
        es1.execute {
            run {
                Thread.sleep(4000)
                Log.v("AndroidTest", "ThreadName = ${Thread.currentThread().name}执行结束")
                latch.countDown()
            }
        }
        val es2 = Executors.newSingleThreadExecutor()
        es2.execute {
            run {
                Thread.sleep(4000)
                Log.v("AndroidTest", "ThreadName = ${Thread.currentThread().name}执行结束")
                latch.countDown()
            }
        }
        t1.start()
        t2.start()
        t3.start()
        latch.await()
        Log.v("AndroidTest", "子线程执行结束, 继续执行主线程")
    }

}