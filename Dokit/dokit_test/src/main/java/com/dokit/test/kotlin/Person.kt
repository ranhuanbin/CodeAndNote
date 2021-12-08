package com.dokit.test.kotlin

/**
 * 主构造函数与次构造函数：
 * 1、使用主构造函数中的参数
 */
class Person(private var name: String) {
    var age = 0
    var sex = "man"

    constructor(name: String, age: Int) : this(name) {
        this.age = age
        println("constructor 1 $name, $age, $sex")
    }

    constructor(name: String, sex: String) : this(name) {
        this.sex = sex;
        println("constructor 2 $name, $age, $sex")
    }

    constructor(name: String, sex: String, age: Int) : this(name, age) {
        this.sex = sex
        this.age = age
        println("constructor 3 $name, $age, $sex")
    }

    open fun learn() {
        println(" learn ===> $name, $sex, $age")
    }

}