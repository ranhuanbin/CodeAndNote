package com.monitor.largeimage.okhttp;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class OkHttpMethodAdapter extends AdviceAdapter {
    public OkHttpMethodAdapter(MethodVisitor methodVisitor, int access, String name, String desc) {
        super(Opcodes.ASM5, methodVisitor, access, name, desc);
    }

    /**
     * 方法退出时插入
     */
    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "okhttp3/OkHttpClient$Builder", "interceptors", "Ljava/util/List;");
        mv.visitMethodInsn(INVOKESTATIC, "com/lib/monitor/largeimage/LargeImage", "getInstance", "()Lcom/lib/monitor/largeimage/LargeImage;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/lib/monitor/largeimage/LargeImage", "getOkHttpInterceptors", "()Ljava/util/List;", false);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "addAll", "(Ljava/util/Collection;)Z", true);
        mv.visitInsn(POP);
    }
}
