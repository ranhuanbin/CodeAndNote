package com.lib.lint

import com.android.SdkConstants.ATTR_SRC
import com.android.resources.ResourceFolderType
import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Detector.UastScanner
import com.android.tools.lint.detector.api.Detector.XmlScanner
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.w3c.dom.Attr
import java.io.File
import java.util.*

class PngDetector : Detector(), XmlScanner, UastScanner {
    private val LINT_EXPLANATION = "WebP can provide better compression than PNG. "

    private val LINT_MSG = "Please use webp instead of png."

    val ISSUE_PNG_IN_XML: Issue = Issue.create(
        "PngUseInXml",
        "Png Usage",
        LINT_EXPLANATION,
        Category.CORRECTNESS,
        5,
        Severity.WARNING,
        Implementation(PngDetector::class.java, Scope.ALL_RESOURCES_SCOPE)
    )

    val ISSUE_PNG_IN_CODE: Issue = Issue.create(
        "PngUseIncode",
        "Png Usage",
        LINT_EXPLANATION,
        Category.CORRECTNESS,
        5,
        Severity.WARNING,
        Implementation(PngDetector::class.java, Scope.JAVA_FILE_SCOPE)
    )

    override fun getApplicableAttributes(): Collection<String>? {
        return Collections.singletonList(ATTR_SRC)
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val srcValue = attribute.value
        if (check(srcValue.substring(10), context.mainProject.resourceFolders)) {
            context.report(ISSUE_PNG_IN_XML, attribute, context.getLocation(attribute), LINT_MSG)
        }
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return Collections.singletonList(UCallExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {

            }
        }
    }

    private fun check(imageName: String, resFolders: MutableList<File>): Boolean {
        var resFolder: File? = null
        for (file in resFolders) {
            if ("res" == file.name) {
                resFolder = File(file.path)
                break
            }
        }
        if (resFolder == null) {
            return false
        }
        val drawableFolder = File(resFolder.path, "drawable")
        if (!drawableFolder.exists() && !drawableFolder.isDirectory) {
            return false
        }
        val filesName = drawableFolder.list()
        if (filesName == null || filesName.isEmpty()) {
            return false
        }
        for (fileName in filesName) {
            if (fileName.substring(0, fileName.indexOf(".")) == imageName) {
                if (fileName.endsWith(".png")) {
                    return true
                }
                return false
            }
        }
        return false
    }

    override fun appliesTo(folderType: ResourceFolderType): Boolean {
        return folderType == ResourceFolderType.LAYOUT
    }

}