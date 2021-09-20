package com.didichuxing.doraemonkit.plugin.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.didichuxing.doraemonkit.plugin.DoKitTransformInvocation
import com.didiglobal.booster.gradle.SCOPE_FULL_PROJECT
import com.didiglobal.booster.gradle.getAndroid
import com.didiglobal.booster.transform.AbstractKlassPool
import com.didiglobal.booster.transform.Transformer
import org.gradle.api.Project
import java.util.*

open class DoKitBaseTransform(val project: Project) : Transform() {
    internal open val transformers = ServiceLoader.load(Transformer::class.java, javaClass.classLoader).toList()
    private lateinit var androidKlassPool: AbstractKlassPool
    private val androidExt: BaseExtension = project.getAndroid()

    init {
        project.afterEvaluate {
            androidKlassPool = object : AbstractKlassPool(androidExt.bootClasspath) {}
        }
    }

    val bootKlassPool: AbstractKlassPool
        get() = androidKlassPool

    override fun getName() = this.javaClass.simpleName

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return when {
            transformers.isEmpty() -> mutableSetOf()
            project.plugins.hasPlugin("com.android.application") -> SCOPE_FULL_PROJECT
            else -> SCOPE_FULL_PROJECT
        }
    }

    override fun isIncremental() = true

    override fun transform(invocation: TransformInvocation) {
        DoKitTransformInvocation(invocation, this).apply {
            if (isIncremental) {
                doIncrementalTransform()
            } else {
                outputProvider?.deleteAll()
                doFullTransform()
            }
        }
    }
}