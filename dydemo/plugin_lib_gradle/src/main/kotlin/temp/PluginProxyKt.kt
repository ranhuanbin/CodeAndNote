package temp

import com.didichuxing.doraemonkit.plugin.println
import java.io.File
import javax.xml.parsers.SAXParserFactory

object PluginProxyKt {

    fun readPluginXML(readText: String, path: String): String {
        "【readPluginXML】path = $path".println()

        val pluginPath = path + File.separator + "plugin.xml"
        val file = File(pluginPath)
        "【readPluginXML】file.exists = ${file.exists()}".println()

        val parser = SAXParserFactory.newInstance().newSAXParser()
        parser.parse(file, XMLParserHandler())
        var replace = readText
        XMLParserHandler.extensionMap.forEach { (key, value) ->
            "【readPluginXML】key = $key，value = $value".println()
            replace = replace.replace("pit_$key", value)
        }
        return replace
    }
}