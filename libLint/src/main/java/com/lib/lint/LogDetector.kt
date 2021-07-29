package com.lib.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import java.util.*


class LogDetector : Detector(), Detector.UastScanner {
    companion object {
        /**
         * 1. 第一个参数是规则的id, 也就是我们上面提到过的规则id
         * 2. 第二个参数是提示信息
         * 3. 第三个参数是具体解释该问题的信息
         * 4. 第四个参数是该规则的规则组
         * 5. 第五个参数是优先级
         * 6. 第六个参数是该规则的警告级别, 这里因为要强制制止这种调用出现, 所以设置为error级别
         * 7. 第七个参数是一个作用域对象, 表明该规则的作用域, 即针对的源代码是java类型(包括kotlin)
         *    源代码
         * */
        val ISSUE: Issue = Issue.create(
            "LogUsage",
            "Log Usage",
            "Please use the unified LogUtil class!",
            Category.CORRECTNESS,
            6,
            Severity.ERROR,
            Implementation(
                LogDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return Collections.singletonList(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return LogHandler(context)
    }

    private inner class LogHandler(val context: JavaContext) : UElementHandler() {
        override fun visitCallExpression(node: UCallExpression) {
            if (!KotlinAdapter.isMethodCall(node)) {
                return
            }
            if (node.receiver != null && node.methodName != null) {
                val methodName = node.methodName
                if (methodName == "i"
                    || methodName == "d"
                    || methodName == "e"
                    || methodName == "v"
                    || methodName == "w"
                    || methodName == "wtf"
                ) {
                    val method = node.resolve()
                    if (context.evaluator.isMemberInClass(method, "android.util.Log")) {
                        reportAllocation(context, node)
                    }
                }
            }
        }
    }

    private fun reportAllocation(context: JavaContext, node: UCallExpression) {
        context.report(ISSUE, node, context.getLocation(node), "avoid using log class directly")
    }
}