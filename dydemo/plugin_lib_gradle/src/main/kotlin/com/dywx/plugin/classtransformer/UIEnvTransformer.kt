package com.dywx.plugin.classtransformer

import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.asIterable
import com.didiglobal.booster.transform.asm.className
import com.dywx.plugin.DyExtUtil
import com.dywx.plugin.config.HeGuiHuaConfig
import com.dywx.plugin.ownerClassName
import com.dywx.plugin.println
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*

/**
 * 对 UI 模块进行插桩，将资源引用进行重定向
 * 目前需要支持插桩的功能清单如下：
 * （1）DialogFragment、Fragment PluginContext进行插桩
 * （2）
 */
class UIEnvTransformer : AbsClassTransformer() {

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
//        klass.methods.forEach {
//            "【UIEnvTransformer】methodName = ${it.name}".println()
//            it.instructions.asIterable().filterIsInstance(MethodInsnNode::class.java).forEach { methodInsnNode ->
//                "【UIEnvTransformer】methodInsnNode =====> name= ${methodInsnNode.name}，desc = ${methodInsnNode.desc}，owner = ${methodInsnNode.owner}，ownerClassName = ${methodInsnNode.ownerClassName}".println()
//            }
//        }

        "【UIEnvTransformer】klass =====> name = ${klass.name}, className = ${klass.className}, superName = ${klass.superName}".println()
        klass.methods.find {
            it.name == "<init>"
        }?.let { methodNode ->
            methodNode.instructions.asIterable()
                .filterIsInstance(MethodInsnNode::class.java).let {
                    "【UIEnvTransformer】1=====>".println()
                    // 方法入口插入，为当前Fragment设置PluginContext
                    methodNode.instructions.insert(insertPluginContext())
                }
        }
        klass.methods.find {
            "【findGetString】methodName = ${it.name}".println()
            it.name == "initData"
        }?.let {
            findGetString(it)
        }
        return klass
    }

    private fun insertPluginContext(): InsnList {
        return with(InsnList()) {
            add(VarInsnNode(ALOAD, 0))
            add(
                MethodInsnNode(
                    INVOKESTATIC,
                    HeGuiHuaConfig.reflectUtil,
                    HeGuiHuaConfig.setPluginContext,
                    HeGuiHuaConfig.setPluginContextDesc,
                    false
                )
            )
            this
        }
    }

    private fun findGetString(methodNode: MethodNode) {
        methodNode.instructions.asIterable().filterIsInstance(MethodInsnNode::class.java).forEach {
            "【findGetString】owner = ${it.owner}, desc = ${it.desc}, name = ${it.name}, ownerClassName = ${it.ownerClassName}".println()
            if (it.name == "getString") {
//                methodNode.instructions.insertBefore(it.previous, modifyString(methodNode))
                it.name = "getStringInner"
            }
        }
    }

    /**
     * todo：不成功啊
     */
    private fun modifyString(methodNode: MethodNode): InsnList {
        return with(InsnList()) {
            add(
                MethodInsnNode(
                    INVOKEVIRTUAL,
                    "com.lib.template.DialogFragmentImpl",
                    "getPluginContext",
                    "()Landroid/content/Context;",
                    false
                )
            )
            this
        }
    }
}

