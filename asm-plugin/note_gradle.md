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

