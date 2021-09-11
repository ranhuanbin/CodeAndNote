## 文章收藏
[Gradle基础 构建生命周期和Hook技术](https://juejin.cn/post/6844903607679057934)


## 1、构建生命周期
任何Gradle的构建过程都分为三部分: 初始化阶段, 配置阶段和执行阶段

## 2、projectsEvaluated介绍
```groovy
void projectsEvaluated(Gradle val) { 
    println '所有项目评估完成(配置阶段完成)'
}
```

## 3、插桩语句问题
注意：**()Landroid/content/Context; 与(Landroid/content/Context;)V** 的区别
```java
add(
    MethodInsnNode(
        INVOKESTATIC,
        "com/dywx/plugin/lib/ContextHolder",
        "getContext",
        "()Landroid/content/Context;",
        false
    )
)
add(
    MethodInsnNode(
        INVOKEVIRTUAL,
        "com/lib/template/FragImpl",
        "setPluginContext",
        "(Landroid/content/Context;)V",
        false
    )
)
```

## 4、初始化阶段
初始化阶段的任务是创建项目的层次结构, 并且为每一个项目创建一个 project 实例, 与初始化阶段相关的脚本文件是 settings.gradle

## 5、配置阶段
    配置阶段的任务是执行各项目下的build.gradle脚本, 完成project的配置, 并且构造Task任务依赖关系图以便在执行阶段按照依赖关系执行Task, 
该阶段也是我们最常接触到的构建阶段, 比如应用外部构建插件 apply plugin:"com.android.application", 配置插件的属性 android{}等.
    每个build.gradle脚本文件对应一个Project对象, 在初始化阶段创建. 
    配置阶段执行的代码包括 build.gradle中的各种语句, 闭包以及Task中的配置段语句, 在根目录的build.gradle中添加如下代码:
```groovy
println 'build.gradle的配置阶段'

// 调用Project的dependencies(Closure c)声明项目依赖
dependencies {
    // 闭包中执行的代码
    println 'dependencies中执行的代码'
}

// 创建一个Task
task test() {
  println 'Task中的配置代码'
  // 定义一个闭包
  def a = {
    println 'Task中的配置代码2'
  }
  // 执行闭包
  a()
  doFirst {
    println '这段代码配置阶段不执行'
  }
}

println '我是顺序执行的'
```
**一定要注意, 配置阶段不仅执行build.gradle中的语句, 还包括了Task中的配置语句**