package com.lib.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import java.util.*

class ThreadDetector : Detector(), Detector.UastScanner {
    val NEW_THREAD = "java.lang.Thread"

    companion object {
        val ISSUE: Issue = Issue.create(
            "ThreadUsage",
            "Thread Usage",
            "Please use ThreadPool, such as AsyncTask.SERIAL_EXECUTOR",
            Category.CORRECTNESS,
            6,
            Severity.WARNING,
            Implementation(ThreadDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return Collections.singletonList(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                if (!KotlinAdapter.isConstructorCall(node)) {
                    return
                }
                val classRef = node.classReference
                if (classRef != null) {
                    val className = KotlinAdapter.getQualifiedName(classRef)
                    if (NEW_THREAD == className && context.project.isAndroidProject) {
                        context.report(
                            ISSUE,
                            node,
                            context.getLocation(node),
                            "avoid call new Thread directly"
                        )
                    }
                }
            }
        }
    }
}