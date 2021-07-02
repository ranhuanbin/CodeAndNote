package com.framework.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.module.IBookManager

class AidlService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.v("AndroidTest", "[AidlService.onBind][process = ${Process.myPid()}][binder = $binder]")
        return binder
    }

    private var binder: IBookManager.Stub = object : IBookManager.Stub() {
        override fun method1(): Int {
            return 1
        }

        override fun method2(value: Int) {

        }
    }
}