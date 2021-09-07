package com.dywx.plugin.classtransformer

import com.dywx.plugin.DyExtUtil
import com.dywx.plugin.isRelease
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.className
import org.objectweb.asm.tree.ClassNode


open class AbsClassTransformer : ClassTransformer {

  fun onCommInterceptor(context: TransformContext, klass: ClassNode): Boolean {
    if (context.isRelease()) {
      return true
    }

    if (!DyExtUtil.dyPluginSwitchOpen()) {
      return true
    }
    //过滤kotlin module-info
    if (klass.className == "module-info") {
      return true
    }

    return false
  }
}
