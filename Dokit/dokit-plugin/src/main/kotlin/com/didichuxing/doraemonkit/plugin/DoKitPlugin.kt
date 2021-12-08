package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.AppExtension
import com.didichuxing.doraemonkit.plugin.extension.CommExt
import com.didichuxing.doraemonkit.plugin.extension.DoKitExt
import com.didichuxing.doraemonkit.plugin.processor.DoKitPluginConfigProcessor
import com.didiglobal.booster.gradle.getAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

class DoKitPlugin : Plugin<Project> {
    override fun apply(project: Project) {


        // 创建指定扩展 并将 project 传入构造函数
//        val dokitExt = project.extensions.create("dokitExt", DoKitExt::class.java)
//
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
}