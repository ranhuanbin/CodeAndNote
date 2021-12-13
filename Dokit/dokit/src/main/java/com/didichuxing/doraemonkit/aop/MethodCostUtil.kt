package com.didichuxing.doraemonkit.aop

import android.app.Application
import android.os.SystemClock
import com.didichuxing.doraemonkit.aop.method_stack.StaticMethodObject
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

object MethodCostUtil {
    private const val TAG = "DOKIT_SLOW_METHOD"

    /**
     * 用来标识是静态函数对象
     */
    private val staticMethodObject: StaticMethodObject by lazy {
        StaticMethodObject()
    }

    private val METHOD_COSTS: ConcurrentHashMap<String, Long?> by lazy {
        ConcurrentHashMap<String, Long?>()
    }

    @Synchronized
    fun recodeObjectMethodCostStart(thresholdTime: Int, methodName: String, classObj: Any?) {
        try {
            METHOD_COSTS[methodName] = SystemClock.elapsedRealtime()
            if (classObj is Application) {
                val methods = methodName.split("&".toRegex()).toTypedArray()
                if (methods.size == 2) {
                    if (methods[1] == "onCreate") {
                        TimeCounterManager.get().onAppCreateStart()
                    }
                    if (methods[1] == "attachBaseContext") {
                        TimeCounterManager.get().onAppAttachBaseContextStart()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}