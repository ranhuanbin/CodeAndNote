package com.monitor.method.stack;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class McStackWeaver extends BaseWeaver {
    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new McStackClassAdapter(classWriter);
    }
}
