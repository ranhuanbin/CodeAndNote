package com.module.kotlin

import android.util.Log

class LateinitTest {
    lateinit var name: String
    var age: String? = null

    fun setNameValue(param: String?) {
        name = param.toString()
    }

    fun printName() {
        Log.v("AndroidTest", "name = $name")
    }

    fun printAge() {
        println(age)
    }
}