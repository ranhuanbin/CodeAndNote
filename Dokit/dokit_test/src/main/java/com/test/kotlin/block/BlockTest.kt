package com.test.kotlin.block

/**
 * https://juejin.cn/post/6844904162782609416
 */
object BlockTest {
    /**
     * 无入参，无返回值
     */
    fun test1(block: () -> Unit) {
        block()
    }

    /**
     * 无入参，有返回值
     */
    fun test2(block: () -> String) {
        val result = block()
        println("test2 ===> result = $result")
    }

    /**
     * 有入参，有返回值
     */
    fun test3(block: (x: Int, y: Int) -> Int) {
        val result = block(1, 2)
        println("test3 ===> result = $result")
    }

    /**
     * 有入参，无返回值
     */
    fun test4(block: (x: Int, y: Int) -> Unit) {
        block(1, 2)
    }
}