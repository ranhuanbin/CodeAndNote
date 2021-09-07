package temp

import com.didichuxing.doraemonkit.plugin.println
import java.io.File
import javax.xml.parsers.SAXParserFactory

object PluginProxyKt {
    private val extensionsTag = mutableListOf<String>()
    private val extensionsMap = HashMap<String, MutableList<String>>()

    fun clear() {
        extensionsTag.clear()
        extensionsMap.clear()
    }

    fun parseExtensionMetaData(name: String, value: String) {
        "【PluginProxyKt】name = $name，value = $value".println()
        when (name) {
            "plugin_extension_type" -> parseExtensionType(value)
            else -> parseExtension(name, value)
        }
    }

    private fun parseExtensionType(value: String) {
        extensionsTag.addAll(value.split(" ").toList())
    }

    /**
     * 为什么 val list = ArrayList<String>()，list不能调用add方法
     * 但是val list = mutableListOf<String>()，list却可以调用add方法
     */
    private fun parseExtension(name: String, value: String) {
        extensionsTag.find {
            it == name
        }?.let {
            val values = extensionsMap.get(name)
            values?.add(value) ?: mutableListOf<String>().let {
                it.add(value)
                extensionsMap.put(name, it)
            }
        }
    }

    /**
     * 重新构造Extension <meta-data构造>
     */
    fun createExtensionMetaData() {
        extensionsMap.forEach { (key, value) ->
            val metadata = "<meta-data android:name=\"$key\" android:value=\"$value\"/>"

        }
    }

    fun printExtension() {
        extensionsMap.forEach { (key, value) ->
            "【printExtension】key = $key，value = $value".println()
        }
    }

    fun readPluginXML(path: String) {
        val pluginPath = path + File.separator + "plugin.xml"
        val file = File(pluginPath)
        println("【readPluginXML】file.exists = ${file.exists()}")

        val parser = SAXParserFactory.newInstance().newSAXParser()
        parser.parse(file, XMLParserHandler())
    }
}