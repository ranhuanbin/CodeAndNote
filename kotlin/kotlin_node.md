# 一、高阶函数

如果一个函数接收另一个函数作为参数，或者返回值的类型是另一个函数，那么该函数就称为高阶函数。

高阶函数涉及到另一个概念：函数类型。

定义一个函数类型，基本规则如下：

```kotlin
(String, Int) -> Unit
```

关键点：

* 1、声明该函数接收什么参数
* 2、声明的函数返回值是什么

因此结合上面伪代码，左边就是用来声明该函数接收什么参数的，多个参数用逗号隔开，如果不接收任何参数，写一对空括号就可以了。而右边部分用于声明该函数的返回值类型，如果没有返回值用 Unit，大致相当于 Java 中的 void。

## 1、高阶函数：伪demo

```kotlin
fun example(func: (String, Int) -> Unit) {
    func("hello", 123)
}
```

example() 函数接收到了一个函数类型参数，因此 example() 就是高阶函数

## 2、高阶函数：原始demo

```kotlin
fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    val result = operation(num1, num2);
    return result
}

fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun main() {
    val num1 = 100
    val num2 = 80
    val result1 = num1AndNum2(num1, num2, ::plus)
    val result2 = num1AndNum2(num1, num2, ::minus)
    println("result1 = $result1")
    println("result2 = $result2")
}

// 输出日志如下：
// result1 = 180
// result2 = 20
```

第三个参数使用了::plus和::minus 的写法，这是一种函数引用方式的写法，表示将 `plus()` 和 minus() 函数作为参数传递给 num1AndNum2() 函数