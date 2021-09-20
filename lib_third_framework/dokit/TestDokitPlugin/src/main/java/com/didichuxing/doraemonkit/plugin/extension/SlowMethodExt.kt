package com.didichuxing.doraemonkit.plugin.extension

import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

open class SlowMethodExt(
        // 函数调用栈模式
        var stackMethod: StackMethodExt = StackMethodExt(),
        // 普通模式
        var normalMethod: NormalMethodExt = NormalMethodExt()) {


    fun stackMethod(closure: Closure<StackMethodExt?>?) {
        ConfigureUtil.configure(closure, stackMethod)
    }

    fun normalMethod(closure: Closure<NormalMethodExt?>?) {
        ConfigureUtil.configure(closure, normalMethod)
    }

    class StackMethodExt(
            // 默认阈值为5ms
            var thresholdTime: Int = 5,
            // 入口函数集合
            var enterMethods: MutableSet<String> = mutableSetOf()) {

        fun thresholdTime(thresholdTime: Int) {
            this.thresholdTime = thresholdTime
        }

        fun normalMethod(enterMethods: MutableSet<String>) {
            this.enterMethods = enterMethods
        }

        override fun toString(): String {
            return "StackMethodExt(thresholdTime=$thresholdTime, enterMethods=$enterMethods)"
        }
    }

    class NormalMethodExt(
            // 默认阈值为500ms
            var thresholdTime: Int = 500,
            // 普通函数的插桩包名集合
            var packageNames: MutableSet<String> = mutableSetOf(),
            // 插桩黑名单
            var methodBlacklist: MutableSet<String> = mutableSetOf()) {

        fun thresholdTime(thresholdTime: Int) {
            this.thresholdTime = thresholdTime
        }

        fun packageNames(packageNames: MutableSet<String>) {
            this.packageNames = packageNames
        }

        fun methodBlacklist(methodBlacklist: MutableSet<String>) {
            this.methodBlacklist = methodBlacklist
        }

        override fun toString(): String {
            return "NormalMethodExt(thresholdTime=$thresholdTime, packageNames=$packageNames, methodBlacklist=$methodBlacklist)"
        }

    }

    companion object {
        const val STRATEGY_STACK = 0
        const val STRATEGY_NORMAL = 1
    }

    override fun toString(): String {
        return "SlowMethodExt(stackMethod=$stackMethod, normalMethod=$normalMethod)"
    }
}