package com.didichuxing.doraemonkit.plugin.transform

import com.didichuxing.doraemonkit.plugin.asmtransformer.DoKitAsmTransformer
import com.didichuxing.doraemonkit.plugin.classtransformer.GlobalSlowMethodTransformer
import com.didiglobal.booster.transform.Transformer
import org.gradle.api.Project

class DoKitCommTransformV34(project: Project) : DoKitBaseTransform(project) {

    override val transformers = listOf<Transformer>(
        DoKitAsmTransformer(
            listOf(
                GlobalSlowMethodTransformer()
            )
        )
    )


}