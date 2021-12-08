package com.test.kotlin.block

/**
 * https://juejin.cn/post/6844904162782609416
 */
object BlockTest {
    /**
     *
     */
    fun test1(block: () -> Unit) {
        block()
    }

    fun test2(block: () -> String) {
        val result = block()
        println("test2 ===> result = $result")
    }

    fun test3(block: (x: Int, y: Int) -> Int) {
        val result = block(1, 2)
        println("test3 ===> result = $result")
    }

    fun test4(block: (x: Int, y: Int) -> Unit) {
        block(1, 2)
    }
}