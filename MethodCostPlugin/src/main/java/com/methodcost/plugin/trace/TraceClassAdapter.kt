package com.methodcost.plugin.trace

import com.methodcost.plugin.trace.MethodVisitorImpl
import com.methodcost.plugin.util.MethodVisitorUtil
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class TraceClassAdapter(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM6, classVisitor) {
    lateinit var className: String
    var isABSClass: Boolean = false
    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        //如果是虚拟类或者接口, isABSClass = true
        if (access.and(Opcodes.ACC_ABSTRACT) > 0 || access.and(Opcodes.ACC_INTERFACE) > 0) {
            this.isABSClass = true
        }
    }

    override fun visitMethod(access: Int, name: String?, desc: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        println("[TraceClassAdapter.visitMethod]\n{\n\tclassName = $className\n\tname = $name\n\tdesc = $desc\n\tsignature = $signature\n}")
        val visitMethod = cv.visitMethod(access, name, desc, signature, exceptions)
        if (MethodVisitorUtil.insertIntercept(className)) {
            return visitMethod
        }
        if (isABSClass) {
            return visitMethod
        }
        return MethodVisitorImpl(visitMethod, access, name, desc, className)
    }


}