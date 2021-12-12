package com.didichuxing.doraemonkit.plugin.asmtransformer

import com.didiglobal.booster.annotations.Priority
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.Transformer
import com.didiglobal.booster.transform.asm.ClassTransformer
import java.lang.management.ManagementFactory
import java.lang.management.ThreadMXBean
import java.util.*


open class BaseDoKitAsmTransformer : Transformer {
    private val threadMxBean = ManagementFactory.getThreadMXBean()

    private val durations = mutableMapOf<ClassTransformer, Long>()

    private val classLoader: ClassLoader

    internal val transformers: Iterable<ClassTransformer>

    constructor() : this(Thread.currentThread().contextClassLoader)

    constructor(classLoader: ClassLoader = Thread.currentThread().contextClassLoader) : this(
        ServiceLoader.load(ClassTransformer::class.java, classLoader).sortedBy {
            it.javaClass.getAnnotation(Priority::class.java)?.value ?: 0
        }, classLoader
    )

    constructor(
        transformers: Iterable<ClassTransformer>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ) {
        this.classLoader = classLoader
        this.transformers = transformers
    }

    override fun onPreTransform(context: TransformContext) {
        this.transformers.forEach { transformer ->
            this.threadMxBean.sumCpuTime(transformer) {
                transformer.onPreTransform(context)
            }
        }
    }

    override fun transform(context: TransformContext, bytecode: ByteArray): ByteArray {
        TODO("Not yet implemented")
    }

    private fun <R> ThreadMXBean.sumCpuTime(transformer: ClassTransformer, action: () -> R): R {
        val ct0 = this.currentThreadCpuTime
        val result = action()
        val ct1 = this.currentThreadCpuTime
        durations[transformer] = durations.getOrDefault(transformer, 0) + (ct1 - ct0)
        return result
    }
}