package com.monitor.largeimage.glide;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import groovyjarjarasm.asm.Opcodes;

public class GlideMethodAdapter extends AdviceAdapter {
    public GlideMethodAdapter(MethodVisitor mv, int access, String methodName, String desc) {
        super(Opcodes.ASM5, mv, access, methodName, desc);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        System.out.println("GlideMethodAdapter.onMethodExit");
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "com/lib/monitor/largeimage/GlideHook", "process", "(Ljava/lang/Object;)V", false);
    }
}
