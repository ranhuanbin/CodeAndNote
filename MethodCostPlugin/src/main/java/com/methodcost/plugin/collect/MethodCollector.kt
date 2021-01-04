package com.methodcost.plugin.collect

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.util.concurrent.ConcurrentHashMap

object MethodCollector {
    private var methodCache: ConcurrentHashMap<String, Boolean> = ConcurrentHashMap()

    public fun collect(classFile: File) {
        val classReader = ClassReader(classFile.readBytes())
        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
        val classVisitor = CollectClassAdapter(classWriter)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
    }

    public fun addMethod(className: String, methodName: String) {
        methodCache["$className&$methodName"] = true
    }

    public fun containMethod(className: String, methodName: String): Boolean {
        return methodCache.contains("$className&$methodName")
    }
}
