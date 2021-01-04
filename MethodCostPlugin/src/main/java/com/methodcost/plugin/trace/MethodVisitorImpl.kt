package com.methodcost.plugin.trace

import com.methodcost.plugin.collect.MethodCollector
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class MethodVisitorImpl(methodVisitor: MethodVisitor, access: Int, name: String?,
                        desc: String?, var className: String)
    : AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, desc) {
    override fun onMethodEnter() {
        if (MethodCollector.containMethod(className, name)) {
            mv.visitLdcInsn("$className&$name")
            mv.visitMethodInsn(INVOKESTATIC, "com/test/apm/methodcost/MethodCostUtil", "i", "(Ljava/lang/String;)V", false)
        }
        println("[onMethodEnter] [name = $name, desc = $methodDesc, className = $className, methodName = $name]")
    }

    override fun onMethodExit(opcode: Int) {
        if (MethodCollector.containMethod(className, name)) {
            mv.visitLdcInsn("$className&$name")
            mv.visitMethodInsn(INVOKESTATIC, "com/test/apm/methodcost/MethodCostUtil", "o", "(Ljava/lang/String;)V", false)
        }
        println("[onMethodExit] [name = $name, desc = $methodDesc, className = $className, methodName = $name]")
    }
}