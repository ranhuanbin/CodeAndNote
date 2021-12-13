package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.api.BaseVariant
import com.android.dex.DexFormat
import com.android.dx.command.dexer.Main
import com.didiglobal.booster.kotlinx.NCPU
import com.didiglobal.booster.kotlinx.redirect
import com.didiglobal.booster.kotlinx.search
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.TransformContext
import com.didiglobal.booster.transform.util.transform
import com.sun.org.apache.bcel.internal.generic.GETFIELD
import com.sun.org.apache.bcel.internal.generic.GETSTATIC
import org.apache.commons.compress.archivers.jar.JarArchiveEntry
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.compress.parallel.InputStreamSupplier
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.MethodNode
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.jar.JarFile
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

fun MethodNode.isGetSetMethod(): Boolean {
    var ignoreCount = 0
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        if (-1 == opcode) {
            continue
        }
        if (opcode != GETFIELD && opcode != GETSTATIC && opcode != H_GETFIELD
            && opcode != H_GETSTATIC && opcode != RETURN && opcode != ARETURN
            && opcode != DRETURN && opcode != FRETURN && opcode != LRETURN
            && opcode != IRETURN && opcode != PUTFIELD && opcode != PUTSTATIC
            && opcode != H_PUTFIELD && opcode != H_PUTSTATIC && opcode > SALOAD
        ) {
            if (name.equals("<init>") && opcode == INVOKESPECIAL) {
                ignoreCount++
                if (ignoreCount > 1) {
                    return false
                }
                continue
            }
            return false
        }
    }
    return true
}

fun MethodNode.isSingleMethod(): Boolean {
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        if (-1 == opcode) {
            continue
        } else if (INVOKEVIRTUAL <= opcode && opcode <= INVOKEDYNAMIC) {
            return false
        }
    }
    return true
}

fun MethodNode.isEmptyMethod(): Boolean {
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        return if (-1 == opcode) {
            continue
        } else {
            false
        }
    }
    return true
}

fun MethodNode.isMainMethod(className: String): Boolean {
    if (this.name == "main" && this.desc == "([Ljava/lang/String;])V") {
        return true
    }
    return false
}

fun String.println() {
    println("[dokit plugin]===>$this")
}

fun InsnList.getMethodExitInsnNodes(): Sequence<InsnNode>? {
    return this.iterator()?.asSequence()?.filterIsInstance(InsnNode::class.java)?.filter {
        it.opcode == RETURN ||
                it.opcode == IRETURN ||
                it.opcode == FRETURN ||
                it.opcode == ARETURN ||
                it.opcode == LRETURN ||
                it.opcode == DRETURN ||
                it.opcode == ATHROW
    }
}

fun BaseVariant.isRelease(): Boolean {
    if (this.name.contains("release") || this.name.contains("Release")) {
        return true
    }
    return false
}

fun TransformContext.isRelease(): Boolean {
    if (this.name.contains("release") || this.name.contains("Release")) {
        return true
    }
    return false
}

internal fun File.dex(output: File, api: Int = DexFormat.API_NO_EXTENDED_OPCODES): Int {
    val args = Main.Arguments().apply {
        numThreads = NCPU
        debug = true
        warnings = true
        emptyOk = true
        multiDex = true
        jarOutput = true
        optimize = false
        minSdkVersion = api
        fileNames = arrayOf(output.canonicalPath)
        outName = canonicalPath
    }
    return try {
        Main.run(args)
    } catch (t: Throwable) {
        t.printStackTrace()
        -1
    }
}

fun File.dokitTransform(output: File, transform: (ByteArray) -> ByteArray = { it -> it }) {
    when {
        isDirectory -> this.toURI().let { base ->
            this.search().parallelStream().forEach {
                it.transform(File(output, base.relativize(it.toURI()).path), transform)
            }
        }
        isFile -> when (extension.toLowerCase()) {
            "jar" -> JarFile(this).use {
                it.dokitTransform(output, ::JarArchiveEntry, transform)
            }
            "class" -> this.inputStream().use {
                it.transform(transform).redirect(output)
            }
            else -> this.copyTo(output, true)
        }
        else -> throw IOException("Unexpected file: ${this.canonicalPath}")
    }
}

fun ZipFile.dokitTransform(
    output: File,
    entryFactory: (ZipEntry) -> ZipArchiveEntry = ::ZipArchiveEntry,
    transform: (ByteArray) -> ByteArray = { it -> it }
) = output.touch().outputStream().buffered().use {
    this.dokitTransform(it, entryFactory, transform)
}

fun ZipFile.dokitTransform(
    output: OutputStream,
    entryFactory: (ZipEntry) -> ZipArchiveEntry = ::ZipArchiveEntry,
    transformer: (ByteArray) -> ByteArray = { it -> it }
) {
    val entries = mutableSetOf<String>()
    val creator = ParallelScatterZipCreator(
        ThreadPoolExecutor(
            NCPU,
            NCPU,
            0L,
            TimeUnit.MICROSECONDS,
            LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(),
            RejectedExecutionHandler { runnable, _ ->
                runnable.run()
            }
        )
    )
    // 将 jar 包里的文件序列化输出
    entries().asSequence().forEach { entry ->
        if (!entries.contains(entry.name)) {
            val zae = entryFactory(entry)

            val stream = InputStreamSupplier {
                when (entry.name.substringAfterLast('.', "")) {
                    "class" -> getInputStream(entry).use { src ->
                        try {
                            src.transform(transformer).inputStream()
                        } catch (e: Throwable) {
                            println("Broken class: ${this.name}!/${entry.name}")
                            getInputStream(entry)
                        }
                    }
                    else -> getInputStream(entry)
                }
            }
            creator.addArchiveEntry(zae, stream)
            entries.add(entry.name)
        } else {
            println("Duplicated jar entry: ${this.name}~/$${entry.name}")
        }
    }
    val zip = ZipArchiveOutputStream(output)
    zip.use { zipStream ->
        try {
            creator.writeTo(zipStream)
            zipStream.close()
        } catch (e: Exception) {
            zipStream.close()
            println("Duplicated jar entry: ${this.name}")
        }
    }
}