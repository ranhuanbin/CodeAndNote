package com.methodcost.plugin.collect

import org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

class CollectClassAdapter(classVisitor: ClassVisitor)
    : ClassVisitor(Opcodes.ASM6, classVisitor) {
    private lateinit var className: String
    private var isABSClass: Boolean = false

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name.toString()
        if (access.and(Opcodes.ACC_ABSTRACT) > 0 || access.and(Opcodes.ACC_INTERFACE) > 0) {
            this.isABSClass = true
        }
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        return when (isABSClass) {
            true -> super.visitMethod(access, name, descriptor, signature, exceptions)
            else -> MethodNodeImpl(className, access, name, descriptor, signature, exceptions)
        }
    }
}