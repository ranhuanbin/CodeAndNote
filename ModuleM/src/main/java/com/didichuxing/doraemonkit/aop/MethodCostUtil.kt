package com.didichuxing.doraemonkit.aop

import android.util.Log
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

object MethodCostUtil {
    const val TAG = "AndroidTest"

    private val METHOD_COSTS: ConcurrentHashMap<String, Long?> by lazy {
        ConcurrentHashMap<String, Long?>()
    }

    @Synchronized
    fun recordObjectMethodCostStart(thresholdTime: Int, methodName: String, classObj: Any?) {
        if (!Thread.currentThread().name.equals("main", true)) {
            return
        }
        try {
            METHOD_COSTS[methodName] = System.currentTimeMillis()
        } catch (e: Exception) {
            println("e = ${e.localizedMessage}")
        }
    }

    fun recordObjectMethodCostEnd(thresholdTime: Int, methodName: String, classObj: Any?) {
        if (!Thread.currentThread().name.equals("main", true)) {
            return
        }
        synchronized(MethodCostUtil::class.java) {
            try {
                if (METHOD_COSTS.containsKey(methodName)) {
                    val startTime = METHOD_COSTS[methodName]!!
                    val costTime = (System.currentTimeMillis() - startTime).toInt()
                    METHOD_COSTS.remove(methodName)
//                    if (costTime >= thresholdTime) {
                        val threadName = Thread.currentThread().name
                        Log.v(TAG, "================Dokit================")
                        Log.v(TAG, "\tmethodName===>$methodName threadName===>$threadName thresholdTime===>$thresholdTime costTime===>$costTime")
                        val stackTraceElements = Thread.currentThread().stackTrace
                        for (stackTraceElement in stackTraceElements) {
                            if (stackTraceElement.toString().contains("MethodCostUtil")) {
                                continue
                            }
                            if (stackTraceElement.toString().contains("dalvik.system.VMStack.getThreadStackTrace")) {
                                continue
                            }
                            if (stackTraceElement.toString().contains("java.lang.Thread.getStackThread")) {
                                continue
                            }
                            Log.v(TAG, "\tat $stackTraceElement")
//                        }
                    }
                }
            } catch (e: Exception) {
//                Log.v(TAG, "e = ${e.localizedMessage}")
            }
        }
    }
}