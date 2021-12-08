package com.didichuxing.doraemonkit.plugin.extension

open class SlowMethodExt(
    var stackMethod: StackMethodExt = StackMethodExt(),
    var normalMethod: NormalMethodExt = NormalMethodExt()
) {
    class StackMethodExt(
        // 默认阈值为 5ms
        var thresholdTime: Int = 5,
        // 入口函数集
        var enterMethods: MutableSet<String> = mutableSetOf(),
        // 插桩黑名单
        var methodBlacklist: MutableSet<String> = mutableSetOf()
    ) {

        fun thresholdTime(thresholdTime: Int) {
            this.thresholdTime = thresholdTime
        }

        fun enterMethods(enterMethods: MutableSet<String>) {
            this.enterMethods = enterMethods
        }

        fun methodBlacklist(methodBlacklist: MutableSet<String>) {
            this.methodBlacklist = methodBlacklist
        }

        override fun toString(): String {
            return "StackMethodExt(thresholdTime=$thresholdTime, enterMethods=$enterMethods, methodBlacklist=$methodBlacklist)"
        }
    }

    class NormalMethodExt(
        // 默认阈值为 5ms
        var thresholdTime: Int = 500,
        // 普通函数的插桩包名集合
        var packageNames: MutableSet<String> = mutableSetOf(),
        // 插桩黑名单
        var methodBlacklist: MutableSet<String> = mutableSetOf()
    ) {

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
}