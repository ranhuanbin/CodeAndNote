package com.module.thread

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.module.R
import kotlinx.android.synthetic.main.activity_thread.*

class ThreadActivity : FragmentActivity() {
    private val lock = Object()
    private lateinit var thread: Thread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("AndroidTest","ThreadActivity onCreate")
        setContentView(R.layout.activity_thread)
        startThread.setOnClickListener { startThread() }
        getThreadState.setOnClickListener { getThreadState() }
    }

    override fun onResume() {
        super.onResume()
        Log.v("AndroidTest","ThreadActivity onResume")
    }
    private fun getThreadState() {
        printThreadState()
    }

    private fun startThread() {
        thread = Thread {
            synchronized(lock) {
                printThreadState()
                lock.wait(5000)
                printThreadState()
                Thread.sleep(5000)
                printThreadState()
            }
        }
        printThreadState()
        thread.start()
    }

    private fun printThreadState() {
        Log.v("AndroidTest", "state = " + thread.state)
    }
}