package com.didichuxing.doraemonkit.plugin.asmtransformer

import com.didiglobal.booster.transform.asm.ClassTransformer

class DoKitAsmTransformer(transformers: Iterable<ClassTransformer>) :
    BaseDoKitAsmTransformer(transformers)