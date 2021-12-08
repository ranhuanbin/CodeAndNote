package com.didichuxing.doraemonkit.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.android.dex.DexFormat
import com.didichuxing.doraemonkit.plugin.transform.DoKitBaseTransform
import com.didiglobal.booster.gradle.*
import com.didiglobal.booster.transform.AbstractKlassPool
import com.didiglobal.booster.transform.ArtifactManager
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.artifacts
import java.io.File
import com.android.build.api.transform.*
import org.gradle.internal.impldep.org.simpleframework.http.Form

class DoKitTransformInvocation(
    private val delegate: TransformInvocation,
    internal val transform: DoKitBaseTransform
) : TransformInvocation by delegate, TransformContext, ArtifactManager {
    private val project = transform.project

    private val outputs = CopyOnWriteArrayList<File>()

    override val name: String = delegate.context.variantName

    override val projectDir: File = project.projectDir

    override val buildDir: File = project.buildDir

    override val temporaryDir: File = delegate.context.temporaryDir

    override val reportsDir: File = File(buildDir, "reports").alse { it.mkdirs() }

    override val bootClasspath = delegate.bootClasspath

    override val compileClasspath = delegate.compileClasspath

    override val runtimeClasspath = delegate.runtimeClasspath

    override val artifacts = this

    override val dependencies: Collection<String> by lazy {
        ResolvedArtifactResults(variant).map {
            it.id.displayName
        }
    }

    override val klassPool: AbstractKlassPool =
        object : AbstractKlassPool(compileClasspath, transform.bootKlassPool) {}

    override val applicationId = delegate.applicationId

    override val originalApplicationId = delegate.originalApplicationId

    override val isDebuggable = variant.buildType.isDebuggable

    override val isDataBindingEnabled = delegate.isDataBindingEnabled

    override fun hasProperty(name: String) = project.hasProperty(name)

    override fun <T> getProperty(name: String, default: T): T = project.getProperty(name, default)

    override fun get(type: String) = variant.artifacts.get(type)

    interface fun doFullTransform() = doTransform(this::transformFully)

    interface fun doIncrementalTransform() = doTransform(this::transformIncrementally)

    private fun onPreTransform() {

    }

    private fun doTransform(block: (ExecutorService) -> Iterable<Future<*>>) {

    }

    private fun transformFully(executor:ExecutorService) = this.inputs.map{

    }

    private fun transformIncrementally(executor:ExecutorService) = this.inputs.map{input->
        input.jarInputs.filter { it.status != Status.NOTCHANGED }.map { jarInput ->
            executor.submit {
                doIncrementalTransform(jarInput)
            }
        } + input.directoryInputs.filter { it.changedFile.isNotEmpty() }.map {dirInput ->
            val base = dirInput.file.toURI()
            executor.submit {
                doIncrementalTransform(dirInput, base)
            }
        }
    }.flatten()

    @Suppress("NON_EXHAUSIVE_WHEN")
    private fun doIncrementalTransform(jarInput: JarInput) {
        when (jarInput.status) {
            Status.REMOVED -> jarInput.file.delete()
            Status.CHANGED, Status.ADDED -> {
                outputProvider?.let { provider ->
                    jarInput.transform(
                        provider.getContentLocation(
                            jarInput.name,
                            jarInput.contentTypes,
                            jarInput.scopes,
                            Format.JAR
                        )
                    )
                }
            }
        }
    }

    @Suppress("NON_EXHAUSTIVE_WHEN")
    private fun doIncrementalTransform(dirInput: DirectoryInput, base: URI) {
        dirInput.changedFiles.forEach { (file, status) ->
            when (status) {
                REMOVED -> {
                    project.logger.info("Deleting $file")
                    outputProvider?.let { provider ->
                        provider.getContentLocation(
                            dirInput.name,
                            dirInput.contentTypes,
                            dirInput.scopes,
                            Format.DIRECTORY
                        ).parentFile.listFiles()?.asSequence()
                            ?.filter { it.isDirectory }
                            ?.map { File(it, dirInput.file.toURI().relativize(file.toURI()).path) }
                            ?.filter { it.exists() }
                            ?.forEach { it.delete() }
                    }
                    file.delete()
                }
                ADDED, CHANGED -> {
                    outputProvider?.let { provider ->
                        val root = provider.getContentLocation(
                            dirInput.name,
                            dirInput.contentTypes,
                            dirInput.scopes,
                            Format.DIRECTORY
                        )
                        val output = File(root, base.relativize(file.toURI()).path)
                        outputs += output
                        file.transform(output) {bytecode ->
                            bytecode.transform()
                        }
                    }
                }
            }
        }
    }

    private fun doVerify() {
        outputs.sortedBy(File::nameWithoutExtension).forEach { output ->
            val out = temporaryDir.file(output.name)
            val rc = out.dex(
                output,
                variant.extension.defaultConfig.targetSdkVersion?.apiLevel
                    ?: DexFormat.API_NO_EXTENDED_OPCODES
            )
            out.deleteRecursively()
        }
    }

    private fun QualifiedContent.transform(output: File) {
        outputs += output
        try {
            this.file.dokitTransform(output) { bytecode ->
                bytecode.transform()
            }
        } catch (e: Exception) {
            "e===>${e.message}".println()
            e.printStackTrace()
        }
    }

    private fun ByteArray.transform():ByteArray{
        return transform.transformers.fold(this){bytes,transformer->
            transformer.transform(this@DoKitTransformInvocation, bytes)
        }
    }
}