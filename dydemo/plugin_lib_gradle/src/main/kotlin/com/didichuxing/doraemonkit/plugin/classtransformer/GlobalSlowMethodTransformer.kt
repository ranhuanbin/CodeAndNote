package com.didichuxing.doraemonkit.plugin.classtransformer

import com.didichuxing.doraemonkit.plugin.DoKitExtUtil
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.className
import org.objectweb.asm.tree.ClassNode

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2020/5/14-18:07
 * 描    述：全局业务代码慢函数  wiki:https://juejin.im/post/5e8d87c4f265da47ad218e6b
 * 修订历史：
 * ================================================
 */
//@Priority(2)
//@AutoService(ClassTransformer::class)
class GlobalSlowMethodTransformer : AbsClassTransformer() {

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)) {
            return klass
        }
        if (DoKitExtUtil.ignorePackageNames(klass.className)) {
            return klass
        }

        val className = klass.className
        return klass
    }

}

