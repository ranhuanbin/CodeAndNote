package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.didichuxing.doraemonkit.plugin.processor.DoKitPluginConfigProcessor
import com.didichuxing.doraemonkit.plugin.transform.DoKitBaseTransform
import com.didichuxing.doraemonkit.plugin.transform.DoKitCommTransform
import com.didichuxing.doraemonkit.plugin.transform.DoKitCommTransformV34
import com.didiglobal.booster.gradle.GTE_V3_4
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.gradle.getProperty
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import com.didichuxing.doraemonkit.plugin.processor.XMLParserHandler.Companion.extensionMap

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2020/5/14-10:01
 * 描    述：
 * 修订历史：
 * ================================================
 */
class DoKitPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        extensionMap.clear()

        project.gradle.addListener(DoKitTransformTaskExecutionListener(project))

        //println("project.plugins===>${project.plugins}")
        /**
         * when 也可以用来取代 if-else if链。
         * 如果不提供参数，所有的分支条件都是简单的布尔表达式，而当一个分支的条件为真时则执行该分支：
         */

        /**
         * 作用域函数:let、run、with、apply 以及 also
         * 它们的唯一目的是在对象的上下文中执行代码块
         * 由于作用域函数本质上都非常相似，因此了解它们之间的区别很重要。每个作用域函数之间有两个主要区别：
         * 引用上下文对象的方式:
         * 作为 lambda 表达式的接收者（this）或者作为 lambda 表达式的参数（it）
         * run、with 以及 apply 通过关键字 this 引用上下文对象
         * let 及 also 将上下文对象作为 lambda 表达式参数
         *
         * 返回值:
         * apply 及 also 返回上下文对象。
         * let、run 及 with 返回 lambda 表达式结果.
         */
        /**
         * 函数	对象引用	   返回值	    是否是扩展函数
         * let	 it	     Lambda 表达式结果	是
         * run	 this	 Lambda 表达式结果	是
         * run	  -	     Lambda 表达式结果	不是：调用无需上下文对象
         * with	 this	 Lambda 表达式结果	不是：把上下文对象当做参数
         * apply this	 上下文对象	        是
         * also	 it	     上下文对象	        是
         */

        /**
         *对一个非空（non-null）对象执行 lambda 表达式：let
         *将表达式作为变量引入为局部作用域中：let
         *对象配置：apply
         *对象配置并且计算结果：run
         *在需要表达式的地方运行语句：非扩展的 run
         *附加效果：also
         *一个对象的一组函数调用：with
         */
        when {
            project.plugins.hasPlugin("com.android.application") || project.plugins.hasPlugin("com.android.dynamic-feature") -> {
                if (!isReleaseTask(project)) {
                    project.getAndroid<AppExtension>().let { androidExt ->
                        val pluginSwitch = project.getProperty("DOKIT_PLUGIN_SWITCH", true)
                        val logSwitch = project.getProperty("DOKIT_LOG_SWITCH", false)
                        DoKitExtUtil.DOKIT_PLUGIN_SWITCH = pluginSwitch
                        DoKitExtUtil.DOKIT_LOG_SWITCH = logSwitch

                        "application module ${project.name} is executing...".println()

                        if (DoKitExtUtil.DOKIT_PLUGIN_SWITCH) {
                            //注册transform
                            androidExt.registerTransform(commNewInstance(project))
                        }

                        //项目评估完毕回调
//                        project.afterEvaluate { project ->
//                            "===afterEvaluate===".println()
//                            androidExt.applicationVariants.forEach { variant ->
//                                DoKitPluginConfigProcessor(project).process(variant)
//                            }
//                        }

                        project.gradle.beforeProject {
                            val task1: Task? = project.tasks.find {
                                it.name == "transformClassesWithTempTransformForDebug"
                            }
                            val task2: Task? = project.tasks.find {
                                it.name == "processDebugManifest"
                            }
                            println("【Task===2】task1 = $task1，task2 = $task2")
                        }

                        /**
                         * 所有项目的build.gradle执行完毕
                         * wiki:https://juejin.im/post/6844903607679057934
                         *
                         * **/
                        project.gradle.projectsEvaluated {
                            "===projectsEvaluated===".println()
                            androidExt.applicationVariants.forEach { variant ->
                                DoKitPluginConfigProcessor(project).process(variant)
                            }
                        }

                        //task依赖关系图建立完毕
                        project.gradle.taskGraph.whenReady {
                            "===taskGraph.whenReady===".println()
                        }
                    }
                }

            }

            project.plugins.hasPlugin("com.android.library") -> {
                if (!isReleaseTask(project)) {
                    project.getAndroid<LibraryExtension>().let { libraryExt ->
                        "library module ${project.name} is executing...".println()
                        if (DoKitExtUtil.DOKIT_PLUGIN_SWITCH) {
                            libraryExt.registerTransform(commNewInstance(project))
                        }
                        project.afterEvaluate {
                            libraryExt.libraryVariants.forEach { variant ->
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