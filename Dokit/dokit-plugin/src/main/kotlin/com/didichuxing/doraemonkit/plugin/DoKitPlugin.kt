package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.AppExtension
import com.didichuxing.doraemonkit.plugin.processor.DoKitPluginConfigProcessor
import com.didichuxing.doraemonkit.plugin.stack_method.MethodStackNodeUtil
import com.didichuxing.doraemonkit.plugin.transform.DoKitBaseTransform
import com.didichuxing.doraemonkit.plugin.transform.DoKitCommTransform
import com.didichuxing.doraemonkit.plugin.transform.DoKitCommTransformV34
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.gradle.getProperty
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.didiglobal.booster.gradle.GTE_V3_4

class DoKitPlugin : Plugin<Project> {
    override fun apply(project: Project) {


        // 创建指定扩展 并将 project 传入构造函数
//        val dokitExt = project.extensions.create("dokitExt", DoKitExt::class.java)
//
        project.gradle.addListener(DoKitTransformTaskExecutionListener(project))
//        val commExt: CommExt =
//            (dokitExt as ExtensionAware).extensions.create("comm", CommExt::class.java)
//
//        dokitExt.toString().println()
//
//        commExt.toString().println()

        when {
            project.plugins.hasPlugin("com.android.application") -> {
                if (!isReleaseTask(project)) {
                    project.getAndroid<AppExtension>().let { androidExt ->
                        val thirdLibInfo = project.getProperty("DOKIT_THIRD_LIB_SWITCH", true)
                        DoKitExtUtil.THIRD_LIBINFO_SWITCH = thirdLibInfo

                        MethodStackNodeUtil.METHOD_STACK_KEYS.clear()
                        if (DoKitExtUtil.DOKIT_PLUGIN_SWITCH) {
                            // 注册 transform
                            androidExt.registerTransform(commNewInstance(project))
                        }

                        // 输出第三方 jar
                        project.gradle.projectsEvaluated {
                            "===projectsEvaluated===".println()
                            androidExt.applicationVariants.forEach { variant ->
                                DoKitPluginConfigProcessor(project).process(variant)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isReleaseTask(project: Project): Boolean {
        return project.gradle.startParameter.taskNames.any {
            it.contains("release") || it.contains("Release")
        }
    }

    private fun commNewInstance(project: Project): DoKitBaseTransform = when {
        GTE_V3_4 -> DoKitCommTransformV34(project)
        else -> DoKitCommTransform(project)
    }

}