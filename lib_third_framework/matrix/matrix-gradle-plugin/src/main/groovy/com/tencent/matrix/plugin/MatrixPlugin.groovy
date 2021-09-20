/*
 * Tencent is pleased to support the open source community by making wechat-matrix available.
 * Copyright (C) 2018 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tencent.matrix.plugin

import com.tencent.matrix.plugin.extension.MatrixDelUnusedResConfiguration
import com.tencent.matrix.trace.extension.MatrixTraceExtension
import com.tencent.matrix.plugin.extension.MatrixExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * matrix {*      trace {*          enable = true
 *          baseMethodMapFile = "${project.projectDir}/matrixTrace/methodMapping.txt"
 *          blackListFile = "${project.projectDir}/matrixTrace/blackMethodList.txt"
 *}*      removeUnusedResours {*          enable true
 *          variant = "debug"
 *          needSign true
 *          shrinkArsc true
 *          //Notice: You need to modify the  value of $apksignerPath on different platform. the value below only suitable for Mac Platform,
 *          //if on Windows, you may have to  replace apksigner with apksigner.bat.
 *          apksignerPath = "${android.getSdkDirectory().getAbsolutePath()}/build-tools/${android.getBuildToolsVersion()}/apksigner.bat"
 *          unusedResources = project.ext.unusedResourcesSet
 *          ignoreResources = ["R.id.*", "R.bool.*"]
 *}*}*/
class MatrixPlugin implements Plugin<Project> {
    private static final String TAG = "Matrix.MatrixPlugin"

    @Override
    void apply(Project project) {
        println("[MatrixPlugin] [apply] [project: $project]")
        // 1.初始化MatrixExtension: clientVersion, uuid, output
        project.extensions.create("matrix", MatrixExtension)
        // 2.初始化MatrixTraceExtension: enable、baseMethodMapFile、blackListFile、customDexTransformName
        project.matrix.extensions.create("trace", MatrixTraceExtension)
        project.matrix.extensions.create("removeUnusedResources", MatrixDelUnusedResConfiguration)
        // todo: {RHB} 这里过滤LIB工程
        if (!project.plugins.hasPlugin('com.android.application')) {
            throw new GradleException('Matrix Plugin, Android Application plugin required')
        }

        project.afterEvaluate {
            def android = project.extensions.android
            def configuration = project.matrix
            def trace = configuration.trace
            android.applicationVariants.all { variant ->
                println("[MatrixPlugin] [apply.afterEvaluate] [android: $android, configuration: $configuration, trace: $trace]")
//                if (configuration.trace.enable) {
//                println("[MatrixPlugin] [apply.afterEvaluate]")
//                MatrixTraceTransform.inject(project, configuration.trace, variant.getVariantData().getScope())
//                }
//                if (configuration.removeUnusedResources.enable) {
//                    if (Util.isNullOrNil(configuration.removeUnusedResources.variant) || variant.name.equalsIgnoreCase(configuration.removeUnusedResources.variant)) {
//                        Log.i(TAG, "removeUnusedResources %s", configuration.removeUnusedResources)
//                        RemoveUnusedResourcesTask removeUnusedResourcesTask = project.tasks.create("remove" + variant.name.capitalize() + "UnusedResources", RemoveUnusedResourcesTask)
//                        removeUnusedResourcesTask.inputs.property(RemoveUnusedResourcesTask.BUILD_VARIANT, variant.name)
//                        project.tasks.add(removeUnusedResourcesTask)
//                        removeUnusedResourcesTask.dependsOn variant.packageApplication
//                        variant.assemble.dependsOn removeUnusedResourcesTask
//                    }
//                }
            }
        }
    }
}
