package com.hb.asm;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;


public class LifecycleMethodVisitor extends MethodVisitor {
    private String className;
    private String methodName;

    public LifecycleMethodVisitor(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM6, methodVisitor);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void visitParameter(String name, int access) {
        System.out.println("LifecycleMethodVisitor.visitParameter()-----name: " + name);
        super.visitParameter(name, access);
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        System.out.println("LifecycleMethodVisitor.visitAnnotationDefault()");
        return super.visitAnnotationDefault();
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        System.out.println("LifecycleMethodVisitor.visitTypeAnnotation()-----desc: " + desc);
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        System.out.println("LifecycleMethodVisitor.visitParameterAnnotation()-----parameter: " + parameter + ", desc: " + desc);
        return super.visitParameterAnnotation(parameter, desc, visible);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("LifecycleMethodVisitor.visitAnnotation()-----desc: " + desc + ", visible: " + visible);
        return super.visitAnnotation(desc, visible);
    }


    //方法执行前插入
    @Override
    public void visitCode() {
        super.visitCode();
        System.out.println("LifecycleMethodVisitor.visitCode()");
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(className + "------->" + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        System.out.println("LifecycleMethodVisitor.visitMethodInsn()-----opcode: " + opcode
                + ", owner: " + owner
                + ", name: " + name
                + ", desc: " + desc
                + ", itf: " + itf
        );
        if ("java/lang/Thread".equals(owner) && "setName".equals(name)) {
            mv.visitInsn(Opcodes.POP);
            mv.visitLdcInsn("original-modified");
        }
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }


    //方法执行后插入
    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        System.out.println("LifecycleMethodVisitor.visitInsn()-----opcode: " + opcode);
        // 判断内部操作指令
        // 当前指令是RETURN，表示方法内部的代码已经执行完
        if (opcode == Opcodes.RETURN) {

        }
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("LifecycleMethodVisitor.visitEnd()");
    }
}
