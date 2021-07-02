package com.framework.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Process
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.module.IBookManager
import com.module.R
import kotlinx.android.synthetic.main.activity_aidl.*

class AidlActivity : FragmentActivity() {
    lateinit var mBinder: IBookManager
    lateinit var mBinder1: IBookManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl)
        Log.v("AndroidTest", "mainProcess = ${Process.myPid()}")
        bindService.setOnClickListener {
            bindService(Intent(this, AidlService::class.java), MyConn(), Context.BIND_AUTO_CREATE)
            bindService(Intent(this, AidlService1::class.java), MyConn1(), Context.BIND_AUTO_CREATE)
        }
        method.setOnClickListener { mBinder.method1() }
    }

    inner class MyConn : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            mBinder = IBookManager.Stub.asInterface(binder)
            Log.v(
                "AndroidTest", "[MyConn][process = ${Process.myPid()}]"
                        + "\n{"
                        + "\n\tservice = $binder, "
                        + "\n\tmBinder = $mBinder" +
                        "\n}"
            )
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    inner class MyConn1 : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            mBinder1 = IBookManager.Stub.asInterface(binder)
            Log.v(
                "AndroidTest", "[MyConn1][process = ${Process.myPid()}]"
                        + "\n{"
                        + "\n\tservice = $binder, "
                        + "\n\tmBinder = $mBinder1" +
                        "\n}"
            )
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
}