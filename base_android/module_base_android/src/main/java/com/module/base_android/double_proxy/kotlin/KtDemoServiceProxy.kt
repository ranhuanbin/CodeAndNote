package com.module.base_android.double_proxy.kotlin

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.atomic.AtomicInteger

class KtDemoServiceProxy : InvocationHandler {
    private val INTEGER = AtomicInteger(1)
    private var target: Any? = null
    private var `in`: Int? = null

    fun DemoServiceProxy() {
        `in` = INTEGER.getAndIncrement()
    }

    override fun invoke(proxy: Any, method: Method, args: Array<Any?>): Any? {
        println("执行前 in=$`in`")
        val `object` = method.invoke(target, *args)
        println("执行后 in=$`in`")
        return `object`
    }

    fun newProxy(target: Any): Any {
        this.target = target
        return Proxy.newProxyInstance(this.javaClass.classLoader, target.javaClass.interfaces, this)
    }
}