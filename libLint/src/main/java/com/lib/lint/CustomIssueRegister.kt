package com.lib.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

/**
 * Android Lint基本使用和自定义规则 https://blog.csdn.net/qq_15827013/article/details/106339741
 */
@SuppressWarnings("UnstableApiUsage")
class CustomIssueRegister : IssueRegistry() {

    override val issues: List<Issue>
        get() = mutableListOf(
            LogDetector.ISSUE,
            PngDetector.ISSUE_PNG_IN_CODE,
            PngDetector.ISSUE_PNG_IN_XML,
            ThreadDetector.ISSUE
        )

    override val api: Int
        get() = KotlinAdapter.getApi()
}