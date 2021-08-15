package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.AppExtension
import com.didichuxing.doraemonkit.plugin.extension.DoKitExt
import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt
import com.didichuxing.doraemonkit.plugin.stack_method.MethodStackNodeUtil
import com.didichuxing.doraemonkit.plugin.transform.DoKitCommTransform
import com.didichuxing.doraemonkit.plugin.transform.DoKitDependTransform
import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.gradle.getProperty
import com.didiglobal.booster.task.spi.VariantProcessor
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

class DoKitPlugin : Plugin<Project> {
    private lateinit var project: Project
    override fun apply(project: Project) {
        this.project = project
        // 创建指定扩展, 并将project传入构造函数
        val doKitExt = project.extensions.create("dokitExt", DoKitExt::class.java)
        when {
            project.plugins.hasPlugin("com.android.application") -> {
                project.getAndroid<AppExtension>().let { androidExt ->
                    println("[DoKitPlugin] project = $project")
                    println("properties = ${project.properties}")
                    val slowMethodSwitch = project.getProperty("DOKIT_METHOD_SWITCH", false)
                    val slowMethodStrategy = project.getProperty("DOKIT_METHOD_STRATEGY", 0)
                    val methodStackLevel = project.getProperty("DOKIT_METHOD_STACK_LEVEL", 5)

                    DoKitExtUtil.mSlowMethodSwitch = slowMethodSwitch
                    DoKitExtUtil.mSlowMethodStrategy = slowMethodStrategy
                    DoKitExtUtil.mStackMethodLevel = methodStackLevel

                    println("[apply][slowMethodSwitch = $slowMethodSwitch, slowMethodStrategy = $slowMethodStrategy, stackMethodLevel = $methodStackLevel]")
                    MethodStackNodeUtil.METHOD_STACK_KEYS.clear()
                    // 注册Transform
                    androidExt.registerTransform(DoKitCommTransform(project))
                    if (slowMethodSwitch && slowMethodStrategy == SlowMethodExt.STRATEGY_STACK) {

                        MethodStackNodeUtil.METHOD_STACK_KEYS.add(0, mutableSetOf<String>())
                        val methodStackRange = 1 until methodStackLevel
                        if (methodStackLevel > 1) {
                            for (index in methodStackRange) {
                                println("[apply][index = $index]")
                                MethodStackNodeUtil.METHOD_STACK_KEYS.add(index, mutableSetOf<String>())
                                androidExt.registerTransform(DoKitDependTransform(project, index))
                            }
                        }
                    }
                    // 项目评估完毕回调
                    project.afterEvaluate {
                        println("[varitantProcessors][1] varitantProcessors = $varitantProcessors")
                        this.varitantProcessors.let { processors ->
                            println("[varitantProcessors][2] processors = $processors")
                            androidExt.applicationVariants.forEach { varient ->
                                println("[varitantProcessors][3] varient = $varient")
                                processors.forEach { processor ->
                                    println("[varitantProcessors][4] processor = $processor")
                                    processor.process(varient)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private val varitantProcessors: Collection<VariantProcessor>
        get() = ServiceLoader.load(VariantProcessor::class.java, javaClass.classLoader).sortedBy {
            println("[varitantProcessors] varitantProcessor = $it")
            it.javaClass.getAnnotation(Priority::class.java)?.value ?: 0
        }

}