##### 1、for循环的几种写法
```kotlin
// 写法一:(正序遍历)
for (index in 1..100) {
    print(index)
}
// 写法二:(倒序遍历)
for (index in 100 downTo 1) {
    print(index)
}
/**
 * 写法三:(正序遍历)
 */
for (index in 1..100 step 2) {
    print(index) // 会输出1, 3, 5
}
/**
 * 写法四: 不包含末尾元素
 */
for (index in 1 until 10) {
    print(index) //输出0..9
}
```

##### 2.定义数组
```kotlin
val array1 = Array(10) { i-> i * i }
val array2 = IntArray(10)
val array3 = arrayOfNulls<Int>(5)
val array4 = intArrayOf(1, 2, 3, 4, 5)
```
##### 3.位运算
```
表达式                 转换方法
shl()           有符号左移(相当于java<<)
shr()           有符号右移(相当于java>>)
ushr()          无符号右移(相当于java>>>)
and()           按位与
or()            按位或
xor()           按位异或
inv()           按位取反
```