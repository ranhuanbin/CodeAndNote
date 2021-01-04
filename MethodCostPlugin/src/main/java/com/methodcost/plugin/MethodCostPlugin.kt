package com.methodcost.plugin

import com.android.build.gradle.AppExtension
import com.didiglobal.booster.gradle.getAndroid
import com.methodcost.plugin.transform.MethodCostTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MethodCostPlugin : Plugin<Project> {
    override fun apply(project: Project) {
//        when {
//            project.plugins.hasPlugin("com.android.application") -> {
//                if (!isReleaseTask(project)) {
//                    project.getAndroid<AppExtension>().let {
//                        it.registerTransform(MethodCostTransform(project))
//                    }
//                }
//            }
//        }
        project.extensions.getByType(AppExtension::class.java).registerTransform(MethodCostTransform(project))
    }

    // 只在测试环境下检测方法耗时
    private fun isReleaseTask(project: Project): Boolean {
        return project.gradle.startParameter.taskNames.any {
            it.contains("release") || it.contains("Release");
        }
    }
}