package com.didichuxing.doraemonkit.plugin.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import com.didichuxing.doraemonkit.plugin.DoKitTransformInvocation
import com.didiglobal.booster.gradle.*
import com.didiglobal.booster.transform.AbstractKlassPool
import com.didiglobal.booster.transform.Transformer
import org.gradle.api.Project
import org.gradle.internal.impldep.com.amazonaws.services.s3.transfer.TransferManager

open class DoKitBaseTransform protected constructor(val project: Project) : Transform() {
    internal open val transforms = listOf<Transformer>()

    internal val verifyEnabled = project.getProperty(OPT_TRANSFORM_VERIFY, false)

    private val android: BaseExtension = project.getAndroid()

    private lateinit var androidKlassPool: AbstractKlassPool

    init {
        project.afterEvaluate {
            androidKlassPool = object : AbstractKlassPool(android.bootClasspath) {}
        }
    }

    val bootKlassPool: AbstractKlassPool
        get() = return androidKlassPool

    override fun getName() = this.javaClass.simpleName

    override fun isIncremental() = !verifyEnabled

    override fun isCacheable() = !verifyEnabled

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> =
        TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = when {
        transforms.isEmpty() -> mutableSetOf()
        project.plugins.hasPlugin("com.android.library") -> SCOPE_PROJECT
        project.plugins.hasPlugin("com.android.application") -> SCOPE_FULL_PROJECT
        project.plugins.hasPlugin("com.android.dynamic-feature") -> SCOPE_FULL_WITH_FEATURES
        else -> TODO("Not an Android project")
    }

    override fun getReferencedScopes(): MutableSet<in QualifiedContent.Scope> = when {
        transformers.isEmpty() -> when {
            project.plugins.hasPlugin("com.android.library") -> SCOPE_PROJECT
            project.plugins.hasPlugin("com.android.application") -> SCOPE_FULL_PROJECT
            project.plugins.hasPlugin("com.android.dynamic-feature") -> SCOPE_FULL_WITH_FEATURES
            else -> TODO("Not an Android project")
        }
        else -> super.getReferencedScopes()
    }

    final override fun transform(invocation: TransformInvocation) {
        DoKitTransformInvocation(invocation, this).apply {
            if (isIncremental) {
                doIncrementalTransform()
            } else {
                outputProvider?.deleteAll()
                duFullTransform()
            }
        }
    }
}


private const val OPT_TRANSFORM_VERIFY = "dokit.transform.verify"