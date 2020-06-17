package com.hb.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class LifecycleMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;

    public LifecycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM6, methodVisitor);
        this.className = className;
        this.methodName = methodName;
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("MethodVisitor visitAnnotation desc------" + desc);
        System.out.println("MethodVisitor visitAnnotation visible------" + visible);
        AnnotationVisitor annotationVisitor = mv.visitAnnotation(desc, visible);
        if (desc.contains("CheckLogin")) {
            return new TestAnnotationVistor(annotationVisitor);
        }
        return annotationVisitor;
    }


    //方法执行前插入
    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("MethodVisitor visitCode------");
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(className + "------->" + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

    //方法执行后插入
    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println("MethodVisitor visitInsn------");
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("MethodVisitor visitEnd------");
    }


}
