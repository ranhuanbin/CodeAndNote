package com.module.base_android.double_proxy

import com.module.base_android.println
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class DemoFeatureProxy : InvocationHandler {
    private lateinit var target: Any

    fun newProxy(target: Any): Any {
        this.target = target
        return Proxy.newProxyInstance(target.javaClass.classLoader, target.javaClass.interfaces, this)
    }

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any? {
        "【DemoFeatureProxy.invoke】执行前".println()
        val invoke = method.invoke(target, args)
        "【DemoFeatureProxy.invoke】执行后".println()
        return invoke
    }
}