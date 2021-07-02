package com.module.apm.fps

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Choreographer

object FpsMonitor {
    private val fpsRunnable by lazy { FpsRunnable() }
    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }

    fun startFpsMonitor() {
        startLooper()
        Choreographer.getInstance().postFrameCallback(fpsRunnable)
    }

    fun stopFpsMonitor() {
        Choreographer.getInstance().removeFrameCallback { fpsRunnable }
    }

    fun startLooper() {
        mainHandler.postDelayed(fpsRunnable, 1000)
    }

    class FpsRunnable : Choreographer.FrameCallback, Runnable {
        private var totalFramesPerSecond = 0
        private var lastFrameRate = 0
        private var lastTime: Long = 0
        override fun doFrame(frameTimeNanos: Long) {
            totalFramesPerSecond++
            Choreographer.getInstance().postFrameCallback(this)
        }

        override fun run() {
            lastFrameRate = totalFramesPerSecond
            Log.v("AndroidTest", "lastFrameRate = $lastFrameRate, totalFramesPerSecond = $totalFramesPerSecond, interval = ${System.currentTimeMillis() - lastTime}")
            lastTime = System.currentTimeMillis()
            totalFramesPerSecond = 0
            mainHandler.postDelayed(fpsRunnable, 1000)
        }
    }
}