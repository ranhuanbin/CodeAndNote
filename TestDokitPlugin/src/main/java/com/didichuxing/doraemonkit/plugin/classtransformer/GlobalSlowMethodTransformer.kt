package com.didichuxing.doraemonkit.plugin.classtransformer

import com.didichuxing.doraemonkit.plugin.DoKitExtUtil
import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt
import com.didichuxing.doraemonkit.plugin.*
import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.asIterable
import com.didiglobal.booster.transform.asm.className
import com.google.auto.service.AutoService
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*

@Priority(2)
@AutoService(ClassTransformer::class)
class GlobalSlowMethodTransformer : ClassTransformer {
    val thresholdTime = DoKitExtUtil.slowMethodExt.normalMethod.thresholdTime
    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (context.isRelease()) {
            return klass
        }
        if (!DoKitExtUtil.dokitPluginSwitchOpen()) {
            return klass
        }
        if (!DoKitExtUtil.dokitSlowMethodSwitchOpen()) {
            return klass
        }
        if (DoKitExtUtil.mSlowMethodStrategy == SlowMethodExt.STRATEGY_STACK) {
            return klass
        }
        if (DoKitExtUtil.ignorePackageNames(klass.className)) {
            return klass
        }
        val className = klass.className
        DoKitExtUtil.slowMethodExt.normalMethod.packageNames.forEach { packageName ->
            println("[GlobalSlowMethodTransformer][packageName = $packageName]")
            // 包含在白名单中且不在黑名单中
            if (className.contains(packageName) && notMatchedBlackList(className)) {
                klass.methods.filter { methodNode ->
                    methodNode.name != "<init>" &&
                            !methodNode.isEmptyMethod() &&
                            !methodNode.isSingleMethod() &&
                            !methodNode.isGetSetMethod()
                }.forEach { methodNode ->
                    methodNode.instructions.asIterable().filterIsInstance(MethodInsnNode::class.java).let { methodInsnNodes ->
                        if (methodInsnNodes.isNotEmpty()) {
                            // 方法入口插入
                            methodNode.instructions.insert(createMethodEnterInsnList(className, methodNode.name, methodNode.access))
                            // 方法出口插入
                            methodNode.instructions.getMethodExtInsnNodes()?.forEach { methodExitInsnNode ->
                                methodNode.instructions.insertBefore(methodExitInsnNode, createMethodExitInsnList(className, methodNode.name, methodNode.access))
                            }
                        }
                    }
                }
            }
        }
        return klass
    }

    private fun notMatchedBlackList(className: String): Boolean {
        for (strBlack in DoKitExtUtil.slowMethodExt.normalMethod.methodBlacklist) {
            if (className.contains(strBlack)) {
                return false
            }
        }
        return true
    }

    /**
     * 创建慢函数入口指令集
     */
    private fun createMethodEnterInsnList(className: String, methodName: String, access: Int): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        return with(InsnList()) {
            if (isStaticMethod) {

            } else {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"))
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(VarInsnNode(ALOAD, 0))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "recordObjectMethodCostStart", "(ILjava/lang/String;Ljava/lang/Object;)V", false))
            }
            this
        }
    }

    /**
     * 创建慢函数退出时的指令集
     */
    private fun createMethodExitInsnList(className: String, methodName: String, access: Int): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        return with(InsnList()) {
            if (isStaticMethod) {

            } else {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"))
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(VarInsnNode(ALOAD, 0))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "recordObjectMethodCostEnd", "(ILjava/lang/String;Ljava/lang/Object;)V", false))
            }
            this
        }
    }
}