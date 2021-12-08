package com.didichuxing.doraemonkit.plugin.extension

open class CommExt
//    var gpsSwitch: Boolean = true,
//    var networkSwitch: Boolean = false,
//    var bigImgSwitch: Boolean = true,
//    var webViewSwitch: Boolean = false,
{
    var gpsSwitch: Boolean = true
    var networkSwitch: Boolean = false
    var bigImgSwitch: Boolean = true
    var webViewSwitch: Boolean = false

    fun gpsSwitch(gpsSwitch: Boolean) {
        this.gpsSwitch = gpsSwitch
    }

    fun networkSwitch(networkSwitch: Boolean) {
        this.networkSwitch = networkSwitch
    }

    fun bigImgSwitch(bigImgSwitch: Boolean) {
        this.bigImgSwitch = bigImgSwitch
    }

    fun webViewSwitch(bigImgSwitch: Boolean) {
        this.bigImgSwitch = bigImgSwitch
    }

    override fun toString(): String {
        return "CommExt(gpsSwitch = $gpsSwitch, networkSwitch = $networkSwitch, bigImgSwitch = $bigImgSwitch, webViewSwitch = $webViewSwitch)"
    }
}