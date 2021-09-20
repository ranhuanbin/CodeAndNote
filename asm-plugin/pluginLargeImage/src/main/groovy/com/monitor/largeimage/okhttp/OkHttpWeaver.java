package com.monitor.largeimage.okhttp;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class OkHttpWeaver extends BaseWeaver {
    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new OkHttpClassAdapter(classWriter);
    }
}
