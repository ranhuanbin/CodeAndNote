package com.didichuxing.doraemonkit.plugin

import com.android.build.gradle.api.BaseVariant
import com.android.dex.DexFormat
import com.android.dx.command.dexer.Main
import com.didiglobal.booster.kotlinx.NCPU
import com.didiglobal.booster.kotlinx.redirect
import com.didiglobal.booster.kotlinx.search
import com.didiglobal.booster.kotlinx.touch
import com.didiglobal.booster.transform.util.transform
import org.apache.commons.compress.archivers.jar.JarArchiveEntry
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.compress.parallel.InputStreamSupplier
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

fun String.println() {
    println("[dokit plugin]===>$this")
}

fun BaseVariant.isRelease(): Boolean {
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