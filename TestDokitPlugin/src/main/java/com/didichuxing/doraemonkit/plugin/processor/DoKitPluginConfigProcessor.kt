package com.didichuxing.doraemonkit.plugin.processor

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.LibraryVariant
import com.didichuxing.doraemonkit.plugin.DoKitExtUtil
import com.didichuxing.doraemonkit.plugin.extension.DoKitExt
import com.didichuxing.doraemonkit.plugin.isRelease
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.gradle.isDynamicFeature
import com.didiglobal.booster.gradle.project
import com.didiglobal.booster.gradle.variantData
import com.didiglobal.booster.task.spi.VariantProcessor
import com.google.auto.service.AutoService

@AutoService(VariantProcessor::class)
class DoKitPluginConfigProcessor : VariantProcessor {
    override fun process(variant: BaseVariant) {
        if (variant is LibraryVariant || variant.variantData.isDynamicFeature()) {
            return
        }
        if (variant.isRelease()) {
            return
        }
        // 读取插件配置信息
        variant.project.getAndroid<AppExtension>().let { appExt ->
            // 查找Application路径
            val doKitExt = variant.project.extensions.getByType(DoKitExt::class.java)
            DoKitExtUtil.init(doKitExt, appExt.defaultConfig.applicationId)
        }
    }
}