package com.dywx.plugin.processor

import com.dywx.plugin.println
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

//const val ATTR_NAME = "android:name"
//
//const val MANIFEST_ATTR_NAME = "package"
//
//const val ATTR_VALUE = "android:value"

class XMLParserHandler : DefaultHandler() {
    private var qName = ""
    private var extensionNames = StringBuilder()
    private var type_extension_value = ""
    override fun startElement(
        uri: String,
        localName: String,
        qName: String,
        attributes: Attributes
    ) {
        if (qName == "type_extension") {
            type_extension_value = attributes.getValue("name")
        }
        this.qName = qName
        "【plugin.xml文件解析 startElement】qName = $qName，type_extension_value = $type_extension_value".println()
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        if (qName == "extension") {
            val s = String(ch, start, length)
            if (s.trim().isNotEmpty()) {
                "【plugin.xml文件解析 characters】extensionNames = ${extensionNames.toString().trim()}".println()
                extensionNames.append(" ").append(s.trim())
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        "【plugin.xml文件解析 endElement】qName = $qName".println()
        if (qName == "type_extension") {
            extensionMap[type_extension_value] = extensionNames.toString().trim()
            extensionNames.setLength(0)
        }
    }

    companion object {
        val extensionMap = mutableMapOf<String, String>()
    }
}