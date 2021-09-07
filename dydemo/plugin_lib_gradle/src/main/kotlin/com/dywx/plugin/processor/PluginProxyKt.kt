package com.dywx.plugin.processor

import com.dywx.plugin.println
import java.io.File
import javax.xml.parsers.SAXParserFactory

object PluginProxyKt {

    fun readPluginXML(readText: String, path: String): String {
        "【plugin.xml文件解析】path = $path".println()

        val pluginPath = path + File.separator + "plugin.xml"
        val file = File(pluginPath)
        "【plugin.xml文件解析】file.exists = ${file.exists()}".println()

        val parser = SAXParserFactory.newInstance().newSAXParser()
        parser.parse(file, XMLParserHandler())
        var replace = readText
        XMLParserHandler.extensionMap.forEach { (key, value) ->
            "【plugin.xml文件解析】key = $key，value = $value".println()
            replace = replace.replace("pit_$key", value)
        }
        return replace
    }
}