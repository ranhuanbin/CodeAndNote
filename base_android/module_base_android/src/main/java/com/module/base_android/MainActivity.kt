package com.module.base_android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.module.base_android.feature.FeatureImpl
import com.module.base_android.feature.IFeature
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Proxy
import java.util.*

class MainActivity : FragmentActivity() {
    val features = mutableMapOf<String, IFeature>(Pair("featureImpl", FeatureImpl()))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dynamicProxyTest.setOnClickListener {
            dynamicProxyTest()
        }
        getFeature.setOnClickListener { getFeature() }

        doubleDynamicProxy.setOnClickListener { doubleDynamicProxy() }
    }

    /*** 对接口类进行动态代理 */
    private fun dynamicProxyTest() {
        val proxyInstance: IFeature = Proxy.newProxyInstance(javaClass.classLoader, arrayOf<Class<*>>(IFeature::class.java)) { _, method, args ->
            "method = $method, args = ${Arrays.toString(args)}".println()
        } as IFeature
        proxyInstance.getString(1, 2)
    }

    private fun getFeature() {
        val feature: FeatureImpl = features["featureImpl"] as FeatureImpl
        "feature = $feature".println()

        val proxyInstance: IFeature = Proxy.newProxyInstance(javaClass.classLoader, arrayOf<Class<*>>(feature.javaClass)) { _, method, args ->
            "method = $method, args = ${Arrays.toString(args)}".println()
        } as IFeature
        proxyInstance.getString(1, 2)
    }

    private fun doubleDynamicProxy() {
//        val featureImpl: FeatureImpl = features["featureImpl"] as FeatureImpl
//        "feature = $featureImpl".println()
//
//        val proxy = FeatureProxy()
//        val feature: IFeature = proxy.newProxy(featureImpl) as IFeature
//
//        feature.getString(1, 2)

//        DoubleProxyTest.test1()

//        val feature: AbstractFeature = DoubleProxyTest.getFeature() as AbstractFeature
//        feature.test()
//        feature.getString(1, 2)

        val featureImpl = FeatureImpl()
        "interfaces = ${featureImpl.javaClass.interfaces.contentToString()}".println()
    }
}