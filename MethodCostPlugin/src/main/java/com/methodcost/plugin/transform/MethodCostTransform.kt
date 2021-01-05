package com.methodcost.plugin.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.methodcost.plugin.collect.MethodCollector
import com.methodcost.plugin.trace.TraceClassAdapter
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File

class MethodCostTransform(project: Project) : Transform() {
    override fun getName(): String {
        return "MethodCostTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 如果设置的是PROJECT_ONLY, 可以只处理文件目录而不用管jar包
     * 拷贝文件这一步必须要做, 不然会出现找不到文件的异常
     */
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return true
    }

    override fun transform(invocation: TransformInvocation) {
        super.transform(invocation)
        if (invocation.isIncremental) {//增量编译
            println("[增量编译]")
        } else {//全量编译
            println("[全量编译]")
            invocation.outputProvider?.deleteAll()
            transformFully(invocation);
        }
    }

    //增量编译
    private fun transformFully(invocation: TransformInvocation) {
        val outputProvider = invocation.outputProvider
        invocation.inputs.forEach { input ->
            input.jarInputs.forEach { jarInput ->
                handleJarInput(jarInput, outputProvider)
            }
            input.directoryInputs.forEach { directoryInput ->
                handleDirectoryInput(directoryInput, outputProvider)
            }
        }
    }

    private fun handleJarInput(jarInput: JarInput, outputProvider: TransformOutputProvider) {
        val dest = outputProvider.getContentLocation(
                jarInput.name,
                jarInput.contentTypes,
                jarInput.scopes,
                Format.JAR
        )
        FileUtils.copyFile(jarInput.file, dest)
    }

    /**
     * 处理class文件
     */
    private fun handleDirectoryInput(directoryInput: DirectoryInput, outputProvider: TransformOutputProvider) {
        directoryInput.file.walk()
                .filter {
                    it.name.endsWith(".class")
                }.iterator().forEach {
                    println("filename = ${it.name}")
                    traceDirectory(it, directoryInput, outputProvider)
                }
    }

    /**
     * 执行插桩逻辑, 在需要插桩方法的入口、出口添加MethodCostUtil.i/o逻辑
     */
    private fun traceDirectory(classFile: File, directoryInput: DirectoryInput, outputProvider: TransformOutputProvider) {
        MethodCollector.collect(classFile)

        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
        val classVisitor = TraceClassAdapter(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)

        classFile.outputStream().write(classWriter.toByteArray())

        FileUtils.copyDirectory(directoryInput.file, outputProvider.getContentLocation(
                directoryInput.name,
                directoryInput.contentTypes,
                directoryInput.scopes,
                Format.DIRECTORY)
        )
    }
}