package com.monitor.method.stack;

import com.monitor.largeimage.Config;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import groovyjarjarasm.asm.Opcodes;

public class McStackMethodAdapter extends AdviceAdapter {
    private String className;

    public McStackMethodAdapter(String classname, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
        this.className = classname;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        mv.visitLdcInsn(Config.getInstance().getThresholdTime());
        mv.visitLdcInsn(className + "&" + getName());
        mv.visitMethodInsn(
                INVOKESTATIC,
                "com/lib/monitor/methodcost/normal/MethodCostUtil",
                "recordObjectMethodCostStart",
                "(ILjava/lang/String;)V",
                false
        );
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        mv.visitLdcInsn(Config.getInstance().getThresholdTime());
        mv.visitLdcInsn(className + "&" + getName());
        mv.visitMethodInsn(
                INVOKESTATIC,
                "com/lib/monitor/methodcost/normal/MethodCostUtil",
                "recordObjectMethodCostEnd",
                "(ILjava/lang/String;)V",
                false
        );
    }

}
