package com.module.base_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Proxy

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dynamicProxyTest.setOnClickListener {
            dynamicProxyTest()
        }
    }

    /*** 对接口类进行动态代理 */
    private fun dynamicProxyTest() {
        Proxy.newProxyInstance(javaClass.classLoader, arrayOf<Class<*>>(IFeature::class.java)) { proxy, method, args ->

        }
    }
}