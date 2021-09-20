package com.plugin.ccregister;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class RegisterMethodAdapter extends AdviceAdapter {
    protected RegisterMethodAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);

    }
}
