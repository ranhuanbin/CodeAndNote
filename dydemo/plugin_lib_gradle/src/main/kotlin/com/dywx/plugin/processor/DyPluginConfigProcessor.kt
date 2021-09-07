package com.dywx.plugin.processor

import com.android.build.gradle.api.BaseVariant
import com.dywx.plugin.DyExtUtil
import com.dywx.plugin.isRelease
import com.dywx.plugin.println
import com.didiglobal.booster.gradle.mergedManifests
import com.didiglobal.booster.task.spi.VariantProcessor
import org.gradle.api.Project
import java.io.File
import javax.xml.parsers.SAXParserFactory

class DyPluginConfigProcessor(val project: Project) : VariantProcessor {
    override fun process(variant: BaseVariant) {
        if (!DyExtUtil.DY_PLUGIN_SWITCH) {
            return
        }

        if (variant.isRelease()) {
            return
        }

        //查找application module下的配置
        if (variant is BaseVariant) {
            println("===DyPluginConfigProcessor.process===【1=====】variant.name = ${variant.name}")
            project.tasks.find {
                //"===task Name is ${it.name}".println()
                val variantName = variant.name.capitalize()
                it.name == "process${variantName}Manifest"
            }?.let { transformTask ->
                transformTask.doFirst {
                    println("===processDebugManifest task doFirst===")
                    variant.mergedManifests.forEach { manifest ->
                        val parser = SAXParserFactory.newInstance().newSAXParser()
                        val handler = DyComponentHandler()
                        "【doFirst】App Manifest path====>$manifest".println()
                        parser.parse(manifest, handler)
                        "【doFirst】App PackageName is====>${handler.appPackageName}".println()
                        "【doFirst】App Application path====>${handler.applications}".println()
                        DyExtUtil.setAppPackageName(handler.appPackageName)
                        DyExtUtil.setApplications(handler.applications)
                        val file: File = manifest
                        val readText = file.readText()
                        val replace = PluginProxyKt.readPluginXML(readText, project.projectDir.path)
                        file.writeText(replace)
                        "【doFirst】readText = $replace".println()
                    }
                }
                transformTask.doLast {
                    println("===processDebugManifest task doLast===")
                    //查找AndroidManifest.xml 文件路径
                    variant.mergedManifests.forEach { manifest ->
                        val parser = SAXParserFactory.newInstance().newSAXParser()
                        val handler = DyComponentHandler()
                        "【doLast】App Manifest path====>$manifest".println()
                        parser.parse(manifest, handler)
                        "【doLast】App PackageName is====>${handler.appPackageName}".println()
                        "【doLast】App Application path====>${handler.applications}".println()
                        DyExtUtil.setAppPackageName(handler.appPackageName)
                        DyExtUtil.setApplications(handler.applications)
                        val file: File = manifest
                        val readText = file.readText()
                        "【doLast】readText = $readText".println()
                    }
                }
            }
        }
    }
}