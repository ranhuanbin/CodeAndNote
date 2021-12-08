package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.api.BaseVariant

fun String.println() {
    println("[dokit plugin]===>$this")
}

fun BaseVariant.isRelease(): Boolean {
    if (this.name.contains("release") || this.name.contains("Release")) {
        return true
    }
    return false
}