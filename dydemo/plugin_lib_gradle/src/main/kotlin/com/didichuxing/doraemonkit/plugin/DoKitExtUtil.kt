package com.didichuxing.doraemonkit.plugin

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2020/3/24-14:58
 * 描    述：
 * 修订历史：
 * ================================================
 */
object DoKitExtUtil {


    /**
     * dokit 插件开关 字段权限必须为public 否则无法进行赋值
     */
    var DOKIT_PLUGIN_SWITCH = true
    var DOKIT_LOG_SWITCH = false


    private val applications: MutableSet<String> = mutableSetOf()

    /**
     * app的packageName
     */
    private var appPackageName: String = ""


    fun dokitPluginSwitchOpen(): Boolean {
        return DOKIT_PLUGIN_SWITCH
    }


    fun dokitLogSwitchOpen(): Boolean {
        return DOKIT_LOG_SWITCH
    }


    fun setApplications(applications: MutableSet<String>) {
        if (applications.isEmpty()) {
            return
        }
        this.applications.clear()
        for (application in applications) {
            this.applications.add(application)
        }
    }

    /**
     * 设置packageName
     */
    fun setAppPackageName(packageName: String) {
        appPackageName = packageName
    }

    fun ignorePackageNames(className: String): Boolean {
        //命中白名单返回false
        for (packageName in whitePackageNames) {
            if (className.startsWith(packageName, true)) {
                return false
            }
        }

        //命中黑名单返回true
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
        "com.didichuxing.doraemonkit.DoKitReal"

    )


    /**
     * 黑名单
     */
    private val blackPackageNames = arrayOf(
        "com.didichuxing.doraemonkit.",
        "kotlin.",
        "java.",
        "android.",
        "androidx."
    )

    fun log(
        tag: String,
        className: String,
        methodName: String,
        access: Int,
        desc: String,
        signature: String,
        thresholdTime: Int
    ) {
        if (DOKIT_LOG_SWITCH) {
            println("$tag===matched====>  className===$className   methodName===$methodName   access===$access   desc===$desc   signature===$signature    thresholdTime===$thresholdTime")
        }
    }

}