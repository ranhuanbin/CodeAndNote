package com.dywx.plugin

object DyExtUtil {


    /**
     * 插件开关 字段权限必须为public 否则无法进行赋值
     */
    var DY_PLUGIN_SWITCH = true
    var DY_LOG_SWITCH = false


    private val applications: MutableSet<String> = mutableSetOf()

    /**
     * app的packageName
     */
    private var appPackageName: String = ""


    fun dyPluginSwitchOpen(): Boolean {
        return DY_PLUGIN_SWITCH
    }


    fun dyLogSwitchOpen(): Boolean {
        return DY_LOG_SWITCH
    }


    fun setApplications(applications: MutableSet<String>) {
        if (applications.isEmpty()) {
            return
        }
        DyExtUtil.applications.clear()
        for (application in applications) {
            DyExtUtil.applications.add(application)
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
    private val whitePackageNames = arrayOf<String>()


    /**
     * 黑名单
     */
    private val blackPackageNames = arrayOf(
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
        if (DY_LOG_SWITCH) {
            println("$tag===matched====>  className===$className   methodName===$methodName   access===$access   desc===$desc   signature===$signature    thresholdTime===$thresholdTime")
        }
    }

}