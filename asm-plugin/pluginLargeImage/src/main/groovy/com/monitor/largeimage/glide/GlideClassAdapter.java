package com.monitor.largeimage.glide;

import com.monitor.largeimage.Config;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class GlideClassAdapter extends ClassVisitor {
    private String className;

    public GlideClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        if (!Config.getInstance().largeImagePluginSwitch()) {
            return mv;
        }
        if (className.equals("com/bumptech/glide/request/SingleRequest")
                && methodName.equals("<init>")
                && desc != null) {
            return new GlideMethodAdapter(mv, access, methodName, desc);
        }
        return mv;
    }
}
