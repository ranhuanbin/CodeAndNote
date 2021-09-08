package com.dywx.plugin.transform

import com.dywx.plugin.asmtransformer.DyAsmTransformer
import com.dywx.plugin.classtransformer.UIEnvTransformer
import com.didiglobal.booster.transform.Transformer
import org.gradle.api.Project

class DyCommTransform(androidProject: Project) : DyBaseTransform(androidProject) {

    override val transformers = listOf<Transformer>(
        DyAsmTransformer(
            listOf(
                UIEnvTransformer()
            )
        )
    )

}
