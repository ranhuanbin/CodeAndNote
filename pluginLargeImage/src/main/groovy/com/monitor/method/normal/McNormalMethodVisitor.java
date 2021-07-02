package com.monitor.method.normal;

import org.objectweb.asm.tree.MethodNode;

import groovyjarjarasm.asm.Opcodes;

public class McNormalMethodVisitor extends MethodNode {
    private String className;

    public McNormalMethodVisitor(String className, int access, String name, String desc,
                                 String signature, String[] exceptions) {
        super(Opcodes.ASM5, access, name, desc, signature, exceptions);
        this.className = className;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
