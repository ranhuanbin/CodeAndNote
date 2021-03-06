package com.didichuxing.doraemonkit.aop.method_stack

import java.util.*

class MethodInvokNode {
    var parent: MethodInvokNode? = null
    var startTimeMillis: Long = 0
    private var endTimeMillis: Long = 0
    private var costTimeMillis = 0
    var currentThreadName: String? = null
    var className: String? = null
    var methodName: String? = null
    var level = 0
    var children: MutableList<MethodInvokNode> = mutableListOf()

    fun getEndTimeMillis(): Long {
        return endTimeMillis
    }

    fun setEndTimeMillis(endTimeMillis: Long) {
        this.endTimeMillis = endTimeMillis
        costTimeMillis = (endTimeMillis - startTimeMillis).toInt()
    }

    fun getCostTimeMillis(): Int {
        return (endTimeMillis - startTimeMillis).toInt()
    }

    fun addChild(methodInvokNode: MethodInvokNode) {
        children.add(methodInvokNode)
    }

    fun setCostTimeMillis(costTimeMillis: Int) {
        this.costTimeMillis = costTimeMillis
    }


}