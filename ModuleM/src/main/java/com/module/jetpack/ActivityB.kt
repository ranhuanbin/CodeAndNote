package com.module.jetpack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleGpsManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ActivityB : FragmentActivity() {
    private val liveData: MutableLiveData<String> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(LifecycleGpsManager())

        liveData.observe(this, Observer {
            Log.v("AndroidTest", "s = $it")
        })
    }
}