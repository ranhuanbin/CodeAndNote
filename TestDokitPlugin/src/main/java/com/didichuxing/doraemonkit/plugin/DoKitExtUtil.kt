package com.didichuxing.doraemonkit.plugin

import com.didichuxing.doraemonkit.plugin.extension.DoKitExt
import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt

object DoKitExtUtil {
    private var mDokitPluginSwitch = true
    private var mDokitLogSwitch = false
    var mStackMethodLevel = 5

    /**
     * 默认函数调用5级
     */
    var mSlowMethodSwitch = false

    /**
     * 慢函数策略, 默认为函数调用栈策略
     */
    var mSlowMethodStrategy = SlowMethodExt.STRATEGY_STACK

    val slowMethodExt = SlowMethodExt()

    /**
     * 白名单
     */
    private val whitePackageNames = arrayOf(
            "com.didichuxing.doraemonkit.DoraemonKit",
            "com.didichuxing.doraemonkit.DoraemonKitReal"
    )

    /**
     * 黑名单
     */
    private val blackPackageNames = arrayOf(
            "com.didichuxing.doraemonkit",
            "kotlin.",
            "java.",
            "android.",
            "androidx."
    )

    fun ignorePackageNames(className: String): Boolean {
        // 命中白名单返回false
        for (packageName in whitePackageNames) {
            if (className.startsWith(packageName, true)) {
                return false
            }
        }
        // 命中黑名单返回true
        for (packageName in blackPackageNames) {
            if (className.startsWith(packageName, true)) {
                return true
            }
        }
        return false
    }


    fun init(dokitExt: DoKitExt, applicationId: String) {
        println("[init][applicationId = $applicationId]")
        // 设置慢函数普通策略插桩包名
        slowMethodExt.normalMethod.packageNames.clear()
        for (packageName in dokitExt.slowMethod.normalMethod.packageNames) {
            slowMethodExt.normalMethod.packageNames.add(packageName)
        }
        // 添加默认的包名
        if (applicationId.isNotEmpty()) {
            if (slowMethodExt.normalMethod.packageNames.isEmpty()) {
                slowMethodExt.normalMethod.packageNames.add(applicationId)
                println("[init][packageNames = ${slowMethodExt.normalMethod.packageNames}]")
            }
        }
    }

    fun dokitPluginSwitchOpen(): Boolean {
        return mDokitPluginSwitch
    }

    fun dokitLogSwitchOpen(): Boolean {
        return mDokitLogSwitch
    }

    fun dokitSlowMethodSwitchOpen(): Boolean {
        return mSlowMethodSwitch
    }
}