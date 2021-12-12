package com.test.kotlin.block

import android.util.Log

/**
 * https://juejin.cn/post/6844904162782609416
 * test1 == test2，唯一不同点是 test2 中将 listener 赋值给成员变量
 */
class BlockTest {

    private lateinit var listener: ((block: Unit) -> Unit)

    private val name: String by lazy {
        "123"
    }

    /**
     * 无入参，无返回值
     */
    fun test1(block: () -> Unit) {
        Log.v("AndroidTest", "BlockTest===>test1===>1")
        block()
        Log.v("AndroidTest", "BlockTest===>test1===>2")
    }

    /**
     * 无入参，无返回值
     */
    fun test2(listener: (block2: Unit) -> Unit) {
        Log.v("AndroidTest", "BlockTest===>test2===>1")
        this.listener = listener
        this.listener?.invoke(Unit)
        Log.v("AndroidTest", "BlockTest===>test2===>2")
    }

    /**
     * 无入参，有返回值
     */
    fun test3(block: () -> String) {
        Log.v("AndroidTest", "BlockTest===>test3===>1")
        val result = block()
        Log.v("AndroidTest", "BlockTest===>test3===>2===>result = $result")
    }

    /**
     * 有入参，有返回值
     */
    fun test4(block: (x: Int, y: Int) -> Int) {
        Log.v("AndroidTest", "BlockTest===>test4===>1")
        val result = block(1, 2)
        Log.v("AndroidTest", "BlockTest===>test4===>2===>result = $result")
    }

    /**
     * 有入参，无返回值
     */
    fun test5(block: (x: Int, y: Int) -> Unit) {
        Log.v("AndroidTest", "BlockTest===>test5===>1")
        block(1, 2)
        Log.v("AndroidTest", "BlockTest===>test5===>2")
    }
}