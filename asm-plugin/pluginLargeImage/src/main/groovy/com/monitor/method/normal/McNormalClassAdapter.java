package com.monitor.method.normal;


import com.monitor.method.McUtils;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class McNormalClassAdapter extends ClassVisitor {
    public String className = "";
    public boolean isABSClass = false;

    public McNormalClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String className, String signature, String superName, String[] interfaces) {
        super.visit(version, access, className, signature, superName, interfaces);
        this.className = className;
        if ((access & Opcodes.ACC_ABSTRACT) > 0 || (access & Opcodes.ACC_INTERFACE) > 0) {
            this.isABSClass = true;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        if (isABSClass) {
            return super.visitMethod(access, methodName, desc, signature, exceptions);
        }
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        if (McUtils.ignorePackageNames(className)) {
            return mv;
        }
        if (!McUtils.whiteList(className)) {
            return mv;
        }
        System.out.println("classname = " + className + ", methodName = " + methodName);
        if (!methodName.equals("<init>")) {
            return new McNormalMethodAdapter(className, mv, access, methodName, desc);
        }
        return mv;
    }
}
