package com.plugin.test

import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension

public class RegisterPlugin implements Plugin<Project> {
    public static final String PLUGIN_NAME = "cc-register"
    public static final String EXT_NAME = "ccregister"

    @Override
    void apply(Project project) {
        println "currentProject: ${project.name}"
        println("RegisterPlugin.apply.1------------------------------------------------------------start\n" +
                "currentProject: ${project}\n" +
                "RegisterPlugin.apply.1------------------------------------------------------------end\n")
        // 1.创建RegisterExtension, 并注册到当前project.extensions中
        project.extensions.create(EXT_NAME, RegisterExtension)
        // 2.判断当前project是否为app
        def isApp = ProjectModuleManager.manageModule(project)
        println("RegisterPlugin.apply.2------------------------------------------------------------start\n" +
                "currentProject: ${project}\n" +
                "isApp: ${isApp}\n" +
                "RegisterPlugin.apply.2------------------------------------------------------------end\n")
        performBuildTypeCache(project, isApp)
        if (isApp) {
            def android = project.extensions.getByType(AppExtension)
            android.each {
                println("RegisterPlugin.apply.3------------------------------------------------------------start\n" +
                        "currentProject: ${project}\n" +
                        "isApp: ${isApp}\n" +
                        "android: ${android}\n" +
                        "it: ${it}\n" +
                        "RegisterPlugin.apply.3------------------------------------------------------------end\n")
            }
            def transformImpl = new RegisterTransform(project)
            /**
             * 注册RegisterTransform
             */
            android.registerTransform(transformImpl)
            project.afterEvaluate {
                println("RegisterPlugin.apply.5------------------------------------------------------------start\n" +
                        "RegisterPlugin.apply.5------------------------------------------------------------end\n")
                RegisterExtension config = init(project, transformImpl)
                //此处要先于transformImpl.transform方法执行
                if (config.multiProcessEnabled) {
                    ManifestGenerator.generateManifestFileContent(project, config.excludeProcessNames)
                }
            }
        }
    }

    private static void performBuildTypeCache(Project project, boolean isApp) {
        if (!RegisterCache.isSameAsLastBuildType(project, isApp)) {
            RegisterCache.cacheBuildType(project, isApp)
            //兼容gradle3.0以上组件独立运行时出现的问题：https://github.com/luckybilly/CC/issues/62
            //切换app/lib编译时，将transform目录清除
            def cachedJniFile = project.file("build/intermediates/transforms/")
            println("performBuildTypeCache.1------------------------------------------------------------start\n" +
                    "currentProject: ${project}\n" +
                    "isApp: ${isApp}\n" +
                    "cachedJniFile: ${cachedJniFile}\n" +
                    "performBuildTypeCache.1------------------------------------------------------------end\n")
            if (cachedJniFile && cachedJniFile.exists() && cachedJniFile.isDirectory()) {
                FileUtils.deleteDirectory(cachedJniFile)
            }
        }
    }

    static RegisterExtension init(Project project, RegisterTransform transformImpl) {
        RegisterExtension extension = project.extensions.findByName(EXT_NAME) as RegisterExtension
        println("RegisterPlugin.init.1------------------------------------------------------------start\n" +
                "extension: ${extension}\n" +
                "RegisterPlugin.init.1------------------------------------------------------------end\n")
        extension.project = project
        extension.convertConfig()
        println("RegisterPlugin.init.2------------------------------------------------------------start\n" +
                "extension: ${extension}\n" +
                "RegisterPlugin.init.2------------------------------------------------------------end\n")
        DefaultRegistryHelper.addDefaultRegistry(extension.list)
        transformImpl.extension = extension
        println("RegisterPlugin.init.3------------------------------------------------------------start\n" +
                "extension: ${extension}\n" +
                "RegisterPlugin.init.3------------------------------------------------------------end\n")
        return extension
    }
}