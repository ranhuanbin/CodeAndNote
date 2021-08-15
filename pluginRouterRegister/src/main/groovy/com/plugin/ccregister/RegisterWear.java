package com.plugin.ccregister;

import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class RegisterWear extends BaseWeaver {
    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new RouterClassAdapter(classWriter);
    }
}
