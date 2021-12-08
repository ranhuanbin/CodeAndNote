package com.dokit.test.kotlin

/**
 * https://blog.csdn.net/guyue35/article/details/105097609
 *
 * init Test
 * init 可以理解为代码块，如果是 companion object 内部的 init，就是静态代码块
 */
class InitTest(var flag: String) {
    /**
     * 次构造方法
     */
    constructor(param: String, flag: String) : this(flag) {
        println("constructor, param = $param, flag = $flag")
    }

    companion object {
        val instance = Person("companion")

        init {
            println("companion init 1")
        }

        init {
            println("companion init 2")
        }
    }

    init {
        println("init 2, flag = $flag")
    }

    init {
        println("init 1, flag = $flag")
    }
}