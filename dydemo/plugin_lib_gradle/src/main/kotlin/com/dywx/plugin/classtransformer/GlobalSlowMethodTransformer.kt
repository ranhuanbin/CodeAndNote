package com.dywx.plugin.classtransformer

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.asIterable
import com.didiglobal.booster.transform.asm.className
import com.dywx.plugin.DyExtUtil
import com.dywx.plugin.println
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode

class GlobalSlowMethodTransformer : AbsClassTransformer() {

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)) {
            return klass
        }
        if (DyExtUtil.ignorePackageNames(klass.className)) {
            return klass
        }
        if (!DyExtUtil.uiNameWhiteList(klass.superName)) {
            return klass
        }
        klass.methods.forEach {
            "【GlobalSlowMethodTransformer】methodName = ${it.name}".println()
        }
        klass.methods.find {
//            it.name == "getLayoutId"
            it.name == "<init>"
        }?.let { methodNode ->
            methodNode.instructions.asIterable()
                .filterIsInstance(MethodInsnNode::class.java).let { methodInsnNodes ->
                    "【GlobalSlowMethodTransformer】1=====>".println()
                    // 方法入口插入，为当前Fragment设置PluginContext
                    methodNode.instructions.insert(insertPluginContext(klass.className, methodNode.name))
                }
        }
        return klass
    }

    fun insertPluginContext(className: String, methodName: String): InsnList {
        return with(InsnList()) {
            add(VarInsnNode(ALOAD, 0))
            add(
                MethodInsnNode(
                    INVOKESTATIC,
                    "com/dywx/plugin/lib/ContextHolder",
                    "getContext",
                    "()Landroid/content/Context;",
                    false
                )
            )
            add(
                MethodInsnNode(
                    INVOKEVIRTUAL,
                    "com/lib/template/FragImpl",
                    "setPluginContext",
                    "(Landroid/content/Context;)V",
                    false
                )
            )
            this
        }
    }
}

