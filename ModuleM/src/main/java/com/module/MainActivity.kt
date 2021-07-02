package com.module

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.framework.aidl.AidlActivity
import com.http.NetManager
import com.lib.monitor.largeimage.LargeImageMonitorActivity
import com.lib.monitor.methodcost.test.MethodCostTest
import com.module.apm.fps.FpsActivity
import com.module.handler.HandlerActivity
import com.module.thread.ThreadActivity
import com.module.thread.ThreadPoolTest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : FragmentActivity() {
    val mainHandler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.RealAppTheme)
        super.onCreate(savedInstanceState)
        Log.v("AndroidTest", "onCreate")
        setContentView(R.layout.activity_main)
        gotoAIDLActivity.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AidlActivity::class.java
                )
            )
        }
        test.setOnClickListener { test() }
        largeImageMonitor.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LargeImageMonitorActivity::class.java
                )
            )
        }
        bfsAndDfs.setOnClickListener { test() }
        threadTest.setOnClickListener { startActivity(Intent(this, ThreadActivity::class.java)) }
        gotoHandleActivity.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HandlerActivity::class.java
                )
            )
        }
        gotoFpsActivity.setOnClickListener { startActivity(Intent(this, FpsActivity::class.java)) }
        newThreadPool.setOnClickListener { ThreadPoolTest.newThreadPool() }
        newCachedThreadPool.setOnClickListener { ThreadPoolTest.newCachedThreadPool() }
        submitTask.setOnClickListener { ThreadPoolTest.submitTask() }
        synchronousQueue.setOnClickListener { NetManager.request() }
    }

    fun test() {
         MethodCostTest().test()

//        ThreadPoolTest.newScheduledThreadPool()
//        Heapsort.test()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            JavaLeetCode.isStraight()
//        }
//        JavaLeetCode.findNumberIn2DArray(5)
//        NotifyWaitTest.notifyWaitTest()

//        Glide.with(this).load("").into(ImageView(this))
//        JavaExample.lastStoneWeight(intArrayOf(2, 7, 4, 1, 8, 1))
//        LeetCode.isPalindrome(121)
//        val threadPool = Executors.newCachedThreadPool()
//        threadPool.execute { Log.v("AndroidTest", "execute") }

//        CountDownLatchTest.test()
//        findMaxAverage(intArrayOf(0, 4, 0, 3, 2), 1)
//        treeNodeTest()
//        JavaLeetCode.printNumbers(1)

//        JavaLeetCode.maxSlidingWindow(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3)

//        JavaLeetCode.reverseWords("a good   example")

//        test0()
//        SystemClock.sleep(1000)
//        Test.test1()

//        JavaLeetCode.quickSort()
//        JavaLeetCode.getLeastNumbers(8)
//        JavaLeetCode.generate(5)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            JavaLeetCode.longestPalindrome("abccccdd")
//        }
//        JavaLeetCode.distributeCandies(7, 4)

    }

    override fun onStart() {
        super.onStart()
        Log.v("AndroidTest", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v("AndroidTest", "onResume")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.v("AndroidTest", "diff = " + (System.currentTimeMillis() - MyApp.attachTime))
    }

    fun test1(block: (Int, Int) -> Int) {
        Log.v("AndroidTest", "[test1] this = $this")
        block(1, 2)
    }

    inline fun <T> T.test2(block: T.() -> Unit): T {
        Log.v("AndroidTest", "[test2] this = $this")
        block()
        return this
    }

    fun test3(block: () -> Unit) {
        Log.v("AndroidTest", "[test3] this = $this")
        block()
    }


    fun test4() {
        Log.v("AndroidTest", "[test4] this = $this")
    }

    fun test0() {
        test2 {
            Log.v("AndroidTest", "[test0] this = $this")
            test4()
        }
        test3 { test4() }
    }

    class Student {
        var name: String = "zhang"
            get() = field.toUpperCase(Locale.ROOT)
    }


    override fun onStop() {
        super.onStop()
        Log.v("AndroidTest", "MainActivity onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.v("AndroidTest", "MainActivity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("AndroidTest", "MainActivity onDestroy")
    }
}