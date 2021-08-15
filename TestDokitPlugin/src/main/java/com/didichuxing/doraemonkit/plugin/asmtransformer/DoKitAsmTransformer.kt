package com.didichuxing.doraemonkit.plugin.asmtransformer

import com.didichuxing.doraemonkit.plugin.classtransformer.MethodStackDepTransformer

class DoKitAsmTransformer(val level: Int) : BaseDoKitAsmTransformer(MethodStackDepTransformer(level)) {

}
