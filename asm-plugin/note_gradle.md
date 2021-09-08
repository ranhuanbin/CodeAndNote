## 文章收藏
[Gradle基础 构建生命周期和Hook技术](https://juejin.cn/post/6844903607679057934)


## 1、构建生命周期

## 2、projectsEvaluated介绍

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