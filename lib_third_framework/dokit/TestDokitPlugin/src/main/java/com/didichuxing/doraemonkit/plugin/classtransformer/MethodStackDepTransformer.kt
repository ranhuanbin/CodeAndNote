package com.didichuxing.doraemonkit.plugin.classtransformer

import com.didichuxing.doraemonkit.plugin.*
import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt
import com.didichuxing.doraemonkit.plugin.stack_method.MethodStackNode
import com.didichuxing.doraemonkit.plugin.stack_method.MethodStackNodeUtil
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.asm.ClassTransformer
import com.didiglobal.booster.transform.asm.asIterable
import com.didiglobal.booster.transform.asm.className
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.*

class MethodStackDepTransformer(private val level: Int = 1) : ClassTransformer {
    private val thresholdTime = DoKitExtUtil.slowMethodExt.stackMethod.thresholdTime
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
        if (DoKitExtUtil.mSlowMethodStrategy == SlowMethodExt.STRATEGY_NORMAL) {
            return klass
        }
        if (DoKitExtUtil.ignorePackageNames(klass.className)) {
            return klass
        }
        val methodStackKeys: MutableSet<String> = MethodStackNodeUtil.METHOD_STACK_KEYS[level - 1]
        klass.methods.filter { methodNode ->
            methodNode.name != "<init>" &&
                    !methodNode.isEmptyMethod() &&
                    !methodNode.isSingleMethod() &&
                    !methodNode.isGetSetMethod()
        }.forEach { methodNode ->
            val key = "${klass.className}&${methodNode.name}&${methodNode.desc}"
//            println("key = $key, methodStackKeys = $methodStackKeys")
            if (methodStackKeys.contains(key)) {
                println("level===>$level  mathched key===>$key")
                operateMethodInsn(klass, methodNode)
            }
        }
        return klass
    }

    private fun operateMethodInsn(klass: ClassNode, methodNode: MethodNode) {
        println("[operateMethodInsn]")
        // 读取全是函数调用的指令
        methodNode.instructions.asIterable().filterIsInstance(MethodInsnNode::class.java).filter { methodInsnNode ->
            methodInsnNode.name != "<init>"
        }.forEach { methodInsnNode ->
            val methodStackNode = MethodStackNode(level, methodInsnNode.ownerClassName, methodInsnNode.name, methodInsnNode.desc,
                    klass.className, methodNode.name, methodNode.desc)
            MethodStackNodeUtil.addMethodStackNode(level, methodStackNode)
        }
        // 函数出入口插入耗时统计代码
        // 方法入口插入
        methodNode.instructions.insert(createMethodEnterInsnList(level, klass.className, methodNode.name, methodNode.desc, methodNode.access))
        // 方法出口插入
        methodNode.instructions.getMethodExtInsnNodes()?.forEach { methodExitInsnNode ->
            methodNode.instructions.insertBefore(methodExitInsnNode, createMethodExitInsnList(level, klass.className, methodNode.name, methodNode.desc, methodNode.access))
        }
    }

    /**
     * 创建慢函数入口指令集
     */
    private fun createMethodEnterInsnList(level: Int, className: String, methodName: String, desc: String, access: Int): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        return with(InsnList()) {
            if (isStaticMethod) {
            } else {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil;"))
                add(IntInsnNode(BIPUSH, DoKitExtUtil.mStackMethodLevel))
                add(IntInsnNode(BIPUSH, thresholdTime))
                add(IntInsnNode(BIPUSH, level))
                add(LdcInsnNode(className))
                add(LdcInsnNode(methodName))
                add(LdcInsnNode(desc))
                add(VarInsnNode(ALOAD, 0))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil", "recodeObjectMethodCostStart", "(IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", false))
            }
            this
        }
    }

    /**
     * 创建慢函数退出时的指令集
     */
    private fun createMethodExitInsnList(level: Int, className: String, methodName: String, desc: String, access: Int): InsnList {
        val isStaticMethod = access and ACC_STATIC != 0
        return with(InsnList()) {
            if (isStaticMethod) {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil;"))
                add(IntInsnNode(BIPUSH, thresholdTime))
                add(IntInsnNode(BIPUSH, level))
                add(LdcInsnNode(className))
                add(LdcInsnNode(methodName))
                add(LdcInsnNode(desc))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil", "recodeStaticMethodCostEnd", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false))
            } else {
                add(FieldInsnNode(GETSTATIC, "com/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil", "INSTANCE", "Lcom/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil;"))
                add(IntInsnNode(BIPUSH, thresholdTime))
                add(IntInsnNode(BIPUSH, level))
                add(LdcInsnNode(className))
                add(LdcInsnNode(methodName))
                add(LdcInsnNode(desc))
                add(VarInsnNode(ALOAD, 0))
                add(MethodInsnNode(INVOKEVIRTUAL, "com/didichuxing/doraemonkit/aop/method_stack/MethodStackUtil", "recodeObjectMethodCostEnd", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", false))
            }
            this
        }
    }
}