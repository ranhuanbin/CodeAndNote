package com.module.jetpack

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleGpsManager
import androidx.lifecycle.ViewModelProvider

class ActivityA : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(LifecycleGpsManager())

        val viewModelA = ViewModelProvider(this).get(ViewModelA::class.java)


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}



