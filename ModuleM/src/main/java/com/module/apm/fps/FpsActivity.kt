package com.module.apm.fps

import android.os.*
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.module.R
import kotlinx.android.synthetic.main.activity_fps.*

class FpsActivity : FragmentActivity() {
    val mainHandler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fps)
        startFpsMonitor.setOnClickListener { FpsMonitor.startFpsMonitor() }
        sleep.setOnClickListener {
            mainHandler.post {
                Log.v("AndroidTest", "sleep start")
                Thread.sleep(2222)
                Log.v("AndroidTest", "sleep end")
            }
        }
        stopFpsMonitor.setOnClickListener { FpsMonitor.stopFpsMonitor() }
        startLooper.setOnClickListener { FpsMonitor.startLooper() }
    }
}