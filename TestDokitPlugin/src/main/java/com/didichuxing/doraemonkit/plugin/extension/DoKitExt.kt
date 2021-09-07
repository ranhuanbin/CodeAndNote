package com.didichuxing.doraemonkit.plugin.extension

import org.gradle.api.Action

open class DoKitExt(
    var dokitPluginSwitch: Boolean = true,
    var dokitLogSwitch: Boolean = false,
    var comm: CommExt = CommExt()
) {

}