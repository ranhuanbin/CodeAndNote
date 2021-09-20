package com.monitor.method.normal;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class McNormalWeaver extends BaseWeaver {
    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new McNormalClassAdapter(classWriter);
    }
}
