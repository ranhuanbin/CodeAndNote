package com.dywx.plugin.transform

import com.android.build.api.variant.VariantInfo
import com.dywx.plugin.asmtransformer.DyAsmTransformer
import com.dywx.plugin.classtransformer.GlobalSlowMethodTransformer
import com.didiglobal.booster.transform.Transformer
import org.gradle.api.Project

internal class DyCommTransformV34(project: Project) : DyBaseTransform(project) {


    override val transformers = listOf<Transformer>(
        DyAsmTransformer(
            listOf(
                GlobalSlowMethodTransformer()
            )
        )
    )

    @Suppress("UnstableApiUsage")
    override fun applyToVariant(variant: VariantInfo): Boolean {
        return variant.buildTypeEnabled || (variant.flavorNames.isNotEmpty() && variant.fullVariantEnabled)
    }

    @Suppress("UnstableApiUsage")
    private val VariantInfo.fullVariantEnabled: Boolean
        get() = project.findProperty("booster.transform.${fullVariantName}.enabled")?.toString()?.toBoolean() ?: true

    @Suppress("UnstableApiUsage")
    private val VariantInfo.buildTypeEnabled: Boolean
        get() = project.findProperty("booster.transform.${buildTypeName}.enabled")?.toString()?.toBoolean() ?: true

}

