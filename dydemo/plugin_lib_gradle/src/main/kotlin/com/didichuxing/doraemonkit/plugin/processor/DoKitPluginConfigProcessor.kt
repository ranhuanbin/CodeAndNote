package com.didichuxing.doraemonkit.plugin.processor

import com.android.build.api.variant.ApplicationVariant
import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import com.didichuxing.doraemonkit.plugin.*
import com.didichuxing.doraemonkit.plugin.extension.DoKitExt
import com.didiglobal.booster.gradle.*
import com.didiglobal.booster.gradle.mergedManifests
import com.didiglobal.booster.task.spi.VariantProcessor
import org.gradle.api.Project
import org.gradle.api.artifacts.result.ResolvedArtifactResult
import java.io.File
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
            DoKitExtUtil.THIRD_LIB_INFOS.clear()
            for (artifactResult: ResolvedArtifactResult in dependencies) {
                //println("三方库信息===>${artifactResult.variant.displayName}____${artifactResult.file.toString()}")
                ///Users/didi/project/android/dokit_github/DoraemonKit/Android/java/app/libs/BaiduLBS_Android.jar
                ///Users/didi/.gradle/caches/modules-2/files-2.1/androidx.activity/activity-ktx/1.2.0/c16aac66e6c4617b01118ab2509f009bb7919b3b/activity-ktx-1.2.0.aar
                //println("三方库信息===>${artifactResult.variant.displayName}____${artifactResult.file.toString()}")
//                "artifactResult===>${artifactResult.file}|${artifactResult.variant}|${artifactResult.id}|${artifactResult.type}".println()
                //"artifactResult===>${artifactResult.variant.owner}|${artifactResult.variant.attributes}|${artifactResult.variant.displayName}|${artifactResult.variant.capabilities}|${artifactResult.variant.externalVariant}".println()
                //"artifactResult===>${artifactResult.variant.displayName}".println()
                val variants = artifactResult.variant.displayName.split(" ")
                var thirdLibInfo: ThirdLibInfo? = null
                if (variants.size == 3) {
                    thirdLibInfo = ThirdLibInfo(
                        variants[0],
                        artifactResult.file.length()
                    )
                    if (thirdLibInfo.variant.contains("dokitx-rpc")) {
                        DoKitExtUtil.HAS_DOKIT_RPC_MODULE = true
                    }
//                    "thirdLibInfo.variant===>${thirdLibInfo.variant}".println()
                    DoKitExtUtil.THIRD_LIB_INFOS.add(thirdLibInfo)
                } else if (variants.size == 4) {
                    thirdLibInfo = ThirdLibInfo(
                        "porject ${variants[1]}",
                        artifactResult.file.length()
                    )
                    if (thirdLibInfo.variant.contains("doraemonkit-rpc")) {
                        DoKitExtUtil.HAS_DOKIT_RPC_MODULE = true
                    }
//                    "thirdLibInfo.variant===>${thirdLibInfo.variant}".println()
                    DoKitExtUtil.THIRD_LIB_INFOS.add(thirdLibInfo)
                }
            }
        }

        println("===DoKitPluginConfigProcessor.process===【variant = $variant】")
        println("===DoKitPluginConfigProcessor.process===【variant is ApplicationVariant】= ${variant is ApplicationVariant}")
        //查找application module下的配置
        if (variant is BaseVariant) {
            println("===DoKitPluginConfigProcessor.process===【1=====】")
            project.tasks.find {
                //"===task Name is ${it.name}".println()
                it.name == "processDebugManifest"
            }?.let { transformTask ->
                transformTask.doFirst {
                    println("===processDebugManifest task doFirst===")
                    variant.mergedManifests.forEach { manifest ->
                        val parser = SAXParserFactory.newInstance().newSAXParser()
                        val handler = DoKitComponentHandler()
                        "【doFirst】App Manifest path====>$manifest".println()
                        parser.parse(manifest, handler)
                        "【doFirst】App PackageName is====>${handler.appPackageName}".println()
                        "【doFirst】App Application path====>${handler.applications}".println()
                        DoKitExtUtil.setAppPackageName(handler.appPackageName)
                        DoKitExtUtil.setApplications(handler.applications)
                        val file: File = manifest
                        val readText = file.readText()
                        "【doFirst】readText = $readText".println()
//                        val replace = readText.replace("12345", "12345_12345")
                        val replace = readText.replace(
                            "<meta-data\n" +
                                    "            android:name=\"123456\"\n" +
                                    "            android:value=\"54321\" />", ""
                        )
                        file.writeText(replace)

//                        PluginProxyKt.printExtension()
                    }
                }
                transformTask.doLast {
                    println("===processDebugManifest task doLast===")

//                    PluginProxyKt.readPluginXML(project.projectDir.path)

                    //查找AndroidManifest.xml 文件路径
                    variant.mergedManifests.forEach { manifest ->
                        val parser = SAXParserFactory.newInstance().newSAXParser()
                        val handler = DoKitComponentHandler()
                        "【doLast】App Manifest path====>$manifest".println()
                        parser.parse(manifest, handler)
                        "【doLast】App PackageName is====>${handler.appPackageName}".println()
                        "【doLast】App Application path====>${handler.applications}".println()
                        DoKitExtUtil.setAppPackageName(handler.appPackageName)
                        DoKitExtUtil.setApplications(handler.applications)
                        val file: File = manifest
                        val readText = file.readText()

                        "【doLast】readText = $readText".println()
                    }

                    //读取插件配置
                    variant.project.getAndroid<AppExtension>().let { appExt ->
                        //查找Application路径
                        val doKitExt = variant.project.extensions.getByType(DoKitExt::class.java)
                        DoKitExtUtil.init(doKitExt)
                    }
                }
            }

        } else {
            "${variant.project.name}-不建议在Library Module下引入dokit插件".println()
        }

    }


}