package com.didichuxing.doraemonkit.plugin.processor

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.internal.pipeline.TransformTask
import com.didichuxing.doraemonkit.plugin.*
import com.didichuxing.doraemonkit.plugin.extension.DoKitExt
import com.didiglobal.booster.gradle.dependencies
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.gradle.mergedManifests
import com.didiglobal.booster.gradle.project
import com.didiglobal.booster.task.spi.VariantProcessor
import com.didiglobal.booster.transform.ArtifactManager
import com.didiglobal.booster.transform.artifacts
import com.didiglobal.booster.transform.util.ComponentHandler
import org.gradle.api.Project
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import java.lang.NullPointerException
import javax.xml.parsers.SAXParserFactory

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2020/5/15-11:28
 * 描    述：
 * 修订历史：
 * ================================================
 */
class DoKitPluginConfigProcessor(val project: Project) : VariantProcessor {
    override fun process(variant: BaseVariant) {
        if (!DoKitExtUtil.DOKIT_PLUGIN_SWITCH) {
            return
        }

        if (variant.isRelease()) {
            return
        }

        //统计三方库信息
        if (DoKitExtUtil.THIRD_LIBINFO_SWITCH) {
            //遍历三方库
            val dependencies = variant.dependencies
//            for (artifactResult: ResolvedArtifactResult in dependencies) {
//                val variants = artifactResult.variant.displayName.split(" ")
//                var thirdLibInfo: ThirdLibInfo? = null
//                if (variants.size == 3) {
//                    thirdLibInfo = ThirdLibInfo(
//                        variants[0],
//                        artifactResult.file.length()
//                    )
//                    if (thirdLibInfo.variant.contains("dokitx-rpc")) {
////                        DoKitExtUtil.HAS_DOKIT_RPC_MODULE = true
//                    }
////                    DoKitExtUtil.THIRD_LIB_INFOS.add(thirdLibInfo)
//                } else if (variants.size == 4) {
//                    thirdLibInfo = ThirdLibInfo(
//                        "porject ${variants[1]}",
//                        artifactResult.file.length()
//                    )
//                    if (thirdLibInfo.variant.contains("doraemonkit-rpc")) {
////                        DoKitExtUtil.HAS_DOKIT_RPC_MODULE = true
//                    }
////                    DoKitExtUtil.THIRD_LIB_INFOS.add(thirdLibInfo)
//                }
//            }
        }
    }
}