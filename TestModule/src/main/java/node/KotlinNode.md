#### 1、for循环的几种写法
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

#### 2.定义数组
```kotlin
val array1 = Array(10) { i-> i * i }
val array2 = IntArray(10)
val array3 = arrayOfNulls<Int>(5)
val array4 = intArrayOf(1, 2, 3, 4, 5)
```
#### 3.位运算
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
#### 4.let、with、run、apply、also函数
##### 4.1 when函数
类似switch/case和if/else
```kotlin
when(view.visibility) {
    View.VISIBLE -> toast("1")
    View.INVISIBLE -> toast("2")
    else -> toast("3")
}

when {
    
}
```
##### 4.2 with函数
```kotlin

```
#### 5. any
Any类型是Kotlin中所有非空类型的根类型
```kotlin
// 相当于java中的if(taskNames != null && !taskNames.isEmpty()) {}
project.gradle.startParameter.taskNames.any {
    it.contains("release") || it.contains("Release")
}
```
#### 6. apply
```
定义类People
class People {
    var name: String = "1"
    var age: Int = 2
    fun hello() {
        println("name = $name, age = $age")
    }
}
使用方式java:
public void test() {
    People p = new People();
    p.setName("1");
    p.hello();
    p.setAge(2);
    p.hello();
    p.hello();
}
使用方式kotlin:
fun test() {
    val p = People()
    p.apply {
        name = "1"
        hello()
        age = 2
    }.hello()
    p.hello()
}

apply为扩展函数, {}内可以直接使用该对象的引用即this
```
#### 7. Block(代替java中的事件回调)
格式: 块名:(参数:参数类型) -> 返回值类型
##### 7.1 无入参, 无返回值(简单回调)
```
// java写法
public interface Function {
    void call();
}

public void setFunction(Function function) {
    mFunction = function;
}

//调用
setFunction(new Function() {
    @Override
    public void call() {
        System.out.println("hello");
    }
});

mFunction.call();
//输出hello
```
```
// kotlin写法
//Kotlin
fun test1(block: () -> Unit) {
    block()
}
//调用
test1 {
    println("hello")
}
//输出hello
```
##### 7.2 无入参, 有返回值 (调用返回一个字符串)
```
//java
public interface Function {
    String call();
}

public void setFunction(Function function) {
    mFunction = function;
}

//调用
setFunction(new Function() {
    @Override
    public String call() {
        return "hello";
    }
});

String value = mFunction.call();
System.out.println("返回值；" + value);

//输出
返回值：hello
```
```
//kotlin
fun test2(block: () -> String) {
    val result = block()
    println(result)
}

//kotlin不需要写return，最后一行则是返回值，这里相当于Java的return "hello"
test2 {
    "返回值：hello"
}

//输出
返回值：hello
```
##### 7.3 有入参, 有返回值 (传两个数字, 返回结果)
```
//java
public interface Function {
    int call(int x, int y);
}

public void setFunction(Function function) {
    mFunction = function;
}

//调用
setFunction(new Function() {
    @Override
    public int call(int x, int y) {
        return x + y;
    }
});

mFunction.call(1, 2);
//输出3
```
```
//kotlin
fun test3(block: (x: Int, y: Int) -> Int) {
    val result = block(1, 2)
    println(result)
}

test3 { x, y ->
    x + y
}
//输出3
```
##### 7.4 有入参, 无返回值 (传两个数字, 回调处打印参数)
```
//java
public interface Function {
    void call(int x, int y);
}

public void setFunction(Function function) {
    mFunction = function;
}

setFunction(new Function() {
    @Override
    public void call(int x, int y) {
        System.out.println("参数1：" + x + "，参数2：" + y);
    }
});

mFunction.call(1, 2);

//输出
参数1：1，参数2：2
```
```
//kotlin
fun test4(block: (x: Int, y: Int) -> Unit) {
    block(1, 2)
}

test4 { x, y ->
    println("参数一：$x，参数二：$y")
}

//输出
参数1：1，参数2：2
```

#### 8. filter函数
```
java写法
for (item in data.item) {
    if (item.useStatus == ORDERSTATUS_WAIT_TO_PAY_ZERO && item.checkExpire == true && item.status == true) {
        mList.add(item)
    }
}

kotlin写法
data.item.filter {
    it.useStatus == ORDERSTATUS_WAIT_TO_PAY_ZERO && it.checkExpire == true && it.status == true
}.foreach {
    mList.add(t)
}
```
