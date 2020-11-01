package com.didichuxing.doraemonkit.plugin.classtransformer

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
            println("[GlobalSlowMethodTransformer] [transform] [1]")
            return klass
        }
        if (!DoKitExtUtil.dokitPluginSwitchOpen()) {
            println("[GlobalSlowMethodTransformer] [transform] [2]")
            return klass
        }
        if (!DoKitExtUtil.dokitSlowMethodSwitchOpen()) {
            println("[GlobalSlowMethodTransformer] [transform] [3]")
            return klass
        }
        if (DoKitExtUtil.SLOW_METHOD_STRATEGY == SlowMethodExt.STRATEGY_STACK) {
            println("[GlobalSlowMethodTransformer] [transform] [4]")
            return klass
        }
        if (DoKitExtUtil.ignorePackageNames(klass.className)) {
            println("[GlobalSlowMethodTransformer] [transform] [5] [className = ${klass.className}]")
            return klass
        }
        val className = klass.className
        //没有自定义设置插装包名 默认是以applicationId为包名 即全局业务代码插桩
        DoKitExtUtil.slowMethodExt.normalMethod.packageNames.forEach { packageName ->
            //包含在白名单中且不在黑名单中
            if (className.contains(packageName) && notMatchedBlackList(className)) {
                klass.methods.filter { methodNode ->
                    // 1.过滤非构造函数
                    methodNode.name != "<init>" &&
                            // 2.过滤空方法
                            !methodNode.isEmptyMethod() &&
                            // 3.过滤没有局部变量的简单方法
                            !methodNode.isSingleMethod() &&
                            // 4.过滤set/get函数
                            !methodNode.isGetSetMethod()
                }.forEach { methodNode ->
                    methodNode.instructions.asIterable().filterIsInstance(MethodInsnNode::class.java).let { methodInsnNodes ->
                        if (methodInsnNodes.isNotEmpty()) {
                            //方法入口插入
                            methodNode.instructions.insert(createMethodEnterInsnList(className, methodNode.name, methodNode.access))
                            //方法出口插入
                            methodNode.instructions.getMethodExitInsnNodes()?.forEach { methodExitInsnNode ->
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
        println("[GlobalSlowMethodTransformer] [方法进入createMethodEnterInsnList] [className = $className, methodName = $methodName, access = $access, isStaticMethod = $isStaticMethod]")
        return with(InsnList()) {
            if (isStaticMethod) {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"))
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "recodeStaticMethodCostStart", "(ILjava/lang/String;)V", false))
            } else {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"))
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(VarInsnNode(ALOAD, 0))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "recodeObjectMethodCostStart", "(ILjava/lang/String;Ljava/lang/Object;)V", false))
            }
            this
        }
    }


    /**
     * 创建慢函数退出时的指令集
     */
    private fun createMethodExitInsnList(className: String, methodName: String, access: Int): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        println("[GlobalSlowMethodTransformer] [方法退出createMethodExitInsnList] [className = $className, methodName = $methodName, access = $access, isStaticMethod = $isStaticMethod]")
        return with(InsnList()) {
            if (isStaticMethod) {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"))
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "recodeStaticMethodCostEnd", "(ILjava/lang/String;)V", false))
            } else {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/MethodCostUtil;"))
                add(IntInsnNode(SIPUSH, thresholdTime))
                add(LdcInsnNode("$className&$methodName"))
                add(VarInsnNode(ALOAD, 0))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/MethodCostUtil", "recodeObjectMethodCostEnd", "(ILjava/lang/String;Ljava/lang/Object;)V", false))
            }
            this
        }
    }


}

