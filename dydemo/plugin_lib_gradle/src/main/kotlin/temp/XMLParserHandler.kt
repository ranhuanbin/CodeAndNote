package temp

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

const val ATTR_NAME = "android:name"

const val MANIFEST_ATTR_NAME = "package"

const val ATTR_VALUE = "android:value"

class XMLParserHandler : DefaultHandler() {

    override fun startElement(
        uri: String,
        localName: String,
        qName: String,
        attributes: Attributes
    ) {
        println("【XMLParserHandler.startElement】qName = $qName")
    }

}