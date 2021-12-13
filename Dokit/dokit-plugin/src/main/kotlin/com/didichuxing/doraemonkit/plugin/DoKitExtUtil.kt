package com.didichuxing.doraemonkit.plugin

import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt

object DoKitExtUtil {

    var DOKIT_PLUGIN_SWITCH = true

    var THIRD_LIBINFO_SWITCH = true

    var SLOW_METHOD_STRATEGY = SlowMethodExt.STRATEGY_STACK

    var SLOW_METHOD_SWITCH = false

    val slowMethodExt = SlowMethodExt()

    fun dokitPluginSwitchOpen(): Boolean {
        return DOKIT_PLUGIN_SWITCH
    }

    fun dokitSlowMethodSwitchOpen(): Boolean {
        return SLOW_METHOD_SWITCH
    }

    fun ignorePackageName(className: String): Boolean {
        for (packageName in whitePackageNames) {
            if (className.startsWith(packageName, true)) {
                return false
            }
        }
        for (packageName in blackPackageNames) {
            if (className.startsWith(packageName, true)) {
                return true
            }
        }
        return false
    }


    /**
     * 白名单
     */
    private val whitePackageNames = arrayOf(
        "com.didichuxing.doraemonkit.DoraemonKit",
        "com.didichuxing.doraemonkit.DoKit",
        "com.didichuxing.doraemonkit.DoKitPool"
    )

    private val blackPackageNames = arrayOf(
        "com.didichuxing.doraemonkit.",
        "kotlin.",
        "java.",
        "android.",
        "androidx."
    )
}