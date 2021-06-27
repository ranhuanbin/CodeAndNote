package com.monitor.largeimage.okhttp;

import com.monitor.largeimage.Config;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class OkHttpClassAdapter extends ClassVisitor {
    private String className;

    public OkHttpClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (!Config.getInstance().largeImagePluginSwitch()) {
            return mv;
        }
        if (className.equals("okhttp3/OkHttpClient$Builder") && name.equals("<init>") && desc.equals("()V")) {
            return mv == null ? null : new OkHttpMethodAdapter(mv, access, name, desc);
        }
        return mv;
    }
}
