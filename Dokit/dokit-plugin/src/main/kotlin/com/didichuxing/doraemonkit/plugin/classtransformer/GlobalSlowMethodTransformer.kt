package com.didichuxing.doraemonkit.plugin.classtransformer

import com.didichuxing.doraemonkit.plugin.*
import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.asIterable
import com.didiglobal.booster.transform.asm.className
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*

class GlobalSlowMethodTransformer : AbsClassTransformer() {
    val thresholdTime = DoKitExtUtil.slowMethodExt.normalMethod.thresholdTime

    override fun transform(context: TransformContext, klass: ClassNode): ClassNode {
        if (onCommInterceptor(context, klass)) {
            return klass
        }

        if (!DoKitExtUtil.dokitSlowMethodSwitchOpen()) {
            return klass
        }

        if (DoKitExtUtil.SLOW_METHOD_STRATEGY == SlowMethodExt.STRATEGY_STACK) {
            return klass
        }

        if (DoKitExtUtil.ignorePackageName(klass.name)) {
            return klass
        }

        val className = klass.className

        DoKitExtUtil.slowMethodExt.normalMethod.packageNames.forEach { packageName ->
            if (className.contains(packageName) && notMatchedBlackList(className)) {
                klass.methods.filter { methodNode ->
                    methodNode.name != "<init>" &&
                            !methodNode.isEmptyMethod() &&
                            !methodNode.isSingleMethod() &&
                            !methodNode.isGetSetMethod() &&
                            !methodNode.isMainMethod(className)
                }.forEach { methodNode ->
                    methodNode.instructions.asIterable()
                        .filterIsInstance(MethodInsnNode::class.java).let { methodInsnNode ->
                            if (methodInsnNode.isNotEmpty()) {
                                // 方法入口插入
                                methodNode.instructions.insert(
                                    createMethodEnterInsnList(
                                        className,
                                        methodNode.name,
                                        methodNode.access
                                    )
                                )
                                // 方法出口插入
                                methodNode.instructions.getMethodExitInsnNodes()
                                    ?.forEach { methodExitInsnNode ->
                                        methodNode.instructions.insertBefore(
                                            methodExitInsnNode,
                                            createMethodExitInsnList(
                                                className,
                                                methodNode.name,
                                                methodNode.access
                                            )
                                        )
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
    private fun createMethodEnterInsnList(
        className: String,
        methodName: String,
        access: Int
    ): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        return with(InsnList()) {
            if (isStaticMethod) {
                add(
                    FieldInsnNode(
                        GETSTATIC,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "INSTANCE",
                        "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"
                    )
                )
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(
                    MethodInsnNode(
                        INVOKEVIRTUAL,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "recodeStaticMethodCostStart",
                        "(ILjava/lang/String;)V",
                        false
                    )
                )
            } else {
                add(
                    FieldInsnNode(
                        GETSTATIC,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "INSTANCE",
                        "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil"
                    )
                )
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(VarInsnNode(ALOAD, 0))
                add(
                    MethodInsnNode(
                        INVOKEVIRTUAL,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "recodeObjectMethodCostStart",
                        "(ILjava/lang/String;Ljava/lang/Object;)V",
                        false
                    )
                )
            }
            this
        }
    }

    /**
     * 创建慢函数退出时的指令集
     */
    private fun createMethodExitInsnList(
        className: String,
        methodName: String,
        access: Int
    ): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        return with(InsnList()) {
            if (isStaticMethod) {
                add(
                    FieldInsnNode(
                        GETSTATIC,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "INSTANCE",
                        "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"
                    )
                )
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(
                    MethodInsnNode(
                        INVOKEVIRTUAL,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "recodeStaticMethodCostEnd",
                        "(ILjava/lang/String;)V",
                        false
                    )
                )
            } else {
                add(
                    FieldInsnNode(
                        GETSTATIC,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "INSTANCE",
                        "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"
                    )
                )
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(VarInsnNode(ALOAD, 0))
                add(
                    MethodInsnNode(
                        INVOKEVIRTUAL,
                        "com/didichuxing/doraemonkit/aop/MethodCostUtil",
                        "recodeObjectMethodCostEnd",
                        "(ILjava/lang/String;Ljava/lang/Object;)V",
                        false
                    )
                )
            }
            this
        }
    }
}