package com.didichuxing.doraemonkit.aop.method_stack

import java.util.*
import java.util.concurrent.ConcurrentHashMap

public object MethodStackUtil {
    private val METHOD_STACKS: MutableList<ConcurrentHashMap<String, MethodInvokeNode>> by lazy {
        Collections.synchronizedList(mutableListOf(ConcurrentHashMap<String, MethodInvokeNode>()))
    }
}