package com.methodcost.plugin.trace

import com.methodcost.plugin.collect.MethodCollector
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class MethodVisitorImpl(methodVisitor: MethodVisitor, access: Int, name: String?,
                        desc: String?, var className: String)
    : AdviceAdapter(Opcodes.ASM6, methodVisitor, access, name, desc) {
    override fun onMethodEnter() {
        println("[onMethodEnter] [1] [name = $name, desc = $methodDesc, className = $className, methodName = $name]")
        if (MethodCollector.containMethod(className, name)) {
            println("[onMethodEnter] [2] [name = $name, desc = $methodDesc, className = $className, methodName = $name]")
            mv.visitLdcInsn("$className&$name")
            mv.visitMethodInsn(INVOKESTATIC, "com/test/apm/methodcost/MethodCostUtil", "i", "(Ljava/lang/String;)V", false)
        }
    }

    override fun onMethodExit(opcode: Int) {
        println("[onMethodExit] [1] [name = $name, desc = $methodDesc, className = $className, methodName = $name]")
        if (MethodCollector.containMethod(className, name)) {
            println("[onMethodExit] [2] [name = $name, desc = $methodDesc, className = $className, methodName = $name]")
            mv.visitLdcInsn("$className&$name")
            mv.visitMethodInsn(INVOKESTATIC, "com/test/apm/methodcost/MethodCostUtil", "o", "(Ljava/lang/String;)V", false)
        }
    }
}