package com.didichuxing.doraemonkit.plugin.stack_method

import java.util.*

object MethodStackNodeUtil {

    val METHOD_STACK_KEYS: MutableList<MutableSet<String>> by lazy {
        Collections.synchronizedList(mutableListOf<MutableSet<String>>())
    }

    fun addMethodStackNode(level: Int, methodStackNode: MethodStackNode) {
        val key =
            "${methodStackNode.className}&${methodStackNode.methodName}&${methodStackNode.desc}"
        METHOD_STACK_KEYS[level].add(key)
    }
}