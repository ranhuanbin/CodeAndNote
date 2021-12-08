package com.dokit.test.kotlin.delete

/**
 * kotlin 类委托
 * https://blog.csdn.net/weixin_38261570/article/details/111500925
 */
interface Printer {
    fun printSomething(something: String)
}

class PrinterDelegate : Printer {
    override fun printSomething(something: String) {
        println("print from delegate : $something")
    }
}

class PrintingHouse(printer: Printer) : Printer by printer

fun main() {
    val delegate = PrinterDelegate()
    val printingHouse = PrintingHouse(delegate)
    printingHouse.printSomething("newspaper")
}