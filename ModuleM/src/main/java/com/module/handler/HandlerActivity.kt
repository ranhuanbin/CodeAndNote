package com.module.handler

import android.os.*
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.module.R
import kotlinx.android.synthetic.main.activity_handler.*

class HandlerActivity : FragmentActivity() {
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        SystemClock.sleep(1000)
        post.setOnClickListener { handler.post { Runnable { Log.v("AndroidTest", "Handler.post"); } } }

    }

    fun postDelay() {
        handler.postDelayed({ Log.v("AndroidTest", "Handler.postDelay") }, 5000L)
    }
}