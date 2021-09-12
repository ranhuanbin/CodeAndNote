package com.dywx.plugin

import com.android.build.gradle.AppExtension
import com.didiglobal.booster.gradle.GTE_V3_4
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.gradle.getProperty
import com.dywx.plugin.processor.DyPluginConfigProcessor
import com.dywx.plugin.processor.XMLParserHandler.Companion.extensionMap
import com.dywx.plugin.transform.DyBaseTransform
import com.dywx.plugin.transform.DyCommTransform
import com.dywx.plugin.transform.DyCommTransformV34
import org.gradle.api.Plugin
import org.gradle.api.Project

class DyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        extensionMap.clear()
        project.gradle.addListener(DyTransformTaskExecutionListener(project))
        val a = HashSet<>()
        when {
            project.plugins.hasPlugin("com.android.application") -> {
                if (!isReleaseTask(project)) {
                    project.getAndroid<AppExtension>().let { androidExt ->
                        val pluginSwitch = project.getProperty("DY_PLUGIN_SWITCH", true)
                        val logSwitch = project.getProperty("DY_LOG_SWITCH", false)
                        DyExtUtil.DY_PLUGIN_SWITCH = pluginSwitch
                        DyExtUtil.DY_LOG_SWITCH = logSwitch

                        "application module ${project.name} is executing...".println()

                        if (DyExtUtil.DY_PLUGIN_SWITCH) {
                            //注册transform
                            androidExt.registerTransform(commNewInstance(project))
                        }

                        //项目评估完毕回调
//                        project.afterEvaluate { project ->
//                            "===afterEvaluate===".println()
//                            androidExt.applicationVariants.forEach { variant ->
//                            }
//                        }

                        /**
                         * 所有项目的build.gradle执行完毕
                         * wiki:https://juejin.im/post/6844903607679057934
                         *
                         * **/
                        project.gradle.projectsEvaluated {
                            "===projectsEvaluated===".println()
                            androidExt.applicationVariants.forEach { variant ->
                                DyPluginConfigProcessor(project).process(variant)
                            }
                        }

                        //task依赖关系图建立完毕
                        project.gradle.taskGraph.whenReady {
                            "===taskGraph.whenReady===".println()
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

    private fun commNewInstance(project: Project): DyBaseTransform = when {
        GTE_V3_4 -> DyCommTransformV34(project)
        else -> DyCommTransform(project)
    }
}