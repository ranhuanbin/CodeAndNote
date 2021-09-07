package com.didichuxing.doraemonkit.plugin.classtransformer

import com.didichuxing.doraemonkit.plugin.DoKitExtUtil
import com.didichuxing.doraemonkit.plugin.isRelease
import com.didichuxing.doraemonkit.plugin.println
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.className
import org.objectweb.asm.tree.ClassNode


open class AbsClassTransformer : ClassTransformer {

  fun onCommInterceptor(context: TransformContext, klass: ClassNode): Boolean {
    if (context.isRelease()) {
      return true
    }

    if (!DoKitExtUtil.dokitPluginSwitchOpen()) {
      return true
    }
    //过滤kotlin module-info
    if (klass.className == "module-info") {
      return true
    }

    return false
  }
}
