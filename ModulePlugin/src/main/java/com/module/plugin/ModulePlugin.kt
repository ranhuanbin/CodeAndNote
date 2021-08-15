package com.module.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class ModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
//        val dokitExt = project.extensions.create("dokitExt", DoKitExt::class.java)
        project.gradle.addListener(TaskExecutionListener(project))
    }
}