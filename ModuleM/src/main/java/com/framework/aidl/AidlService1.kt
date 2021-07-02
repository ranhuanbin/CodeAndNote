package com.framework.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.module.IBookManager.Stub

class AidlService1 : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.v("AndroidTest", "[AidlService1.onBind][process = ${Process.myPid()}][binder = $binder]")
        return binder
    }

    private var binder: Stub = object : Stub() {
        override fun method1(): Int {
            return 1
        }

        override fun method2(value: Int) {

        }
    }
}