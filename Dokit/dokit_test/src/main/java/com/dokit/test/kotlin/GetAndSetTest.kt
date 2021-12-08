package com.dokit.test.kotlin

class GetAndSetTest {
    val getAndSetTest1: GetAndSetTest
        get() = GetAndSetTest()

    val getAndSetTest2: GetAndSetTest
        get() {
            return GetAndSetTest()
        }

}