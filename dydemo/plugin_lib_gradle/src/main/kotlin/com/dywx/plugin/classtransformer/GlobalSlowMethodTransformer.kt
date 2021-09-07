package com.dywx.plugin.classtransformer

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.className
import com.dywx.plugin.DyExtUtil
import org.objectweb.asm.tree.ClassNode

class GlobalSlowMethodTransformer : AbsClassTransformer() {

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)) {
            return klass
        }
        if (DyExtUtil.ignorePackageNames(klass.className)) {
            return klass
        }

        val className = klass.className
        return klass
    }

}

