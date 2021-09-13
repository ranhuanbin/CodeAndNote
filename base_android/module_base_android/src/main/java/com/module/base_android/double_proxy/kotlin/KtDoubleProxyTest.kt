package com.module.base_android.double_proxy.kotlin

class KtDoubleProxyTest {
    companion object {
        fun test() {
            val demoService: KtDemoServiceImpl = KtDemoServiceImpl()
            val proxy = KtDemoServiceProxy()
            val serviceKt: KtDemoService = proxy.newProxy(proxy) as KtDemoService
            val proxy1 = KtDemoServiceProxy()
            val service1Kt: KtDemoService = proxy1.newProxy(serviceKt) as KtDemoService

            service1Kt.saiHi("999")
        }
    }
}