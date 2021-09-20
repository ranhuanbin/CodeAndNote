package com.plugin.ccregister;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.List;

public class RouterClassAdapter extends ClassVisitor {

    public RouterClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        List<String> strings = Arrays.asList(interfaces);
        if (strings.contains("com.lib.router.IComponent")) {
            ScanContainer.add(name);
        }
    }
}
