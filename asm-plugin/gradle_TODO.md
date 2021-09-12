# 文章收藏
[协程 路由 组件化 1+1+1>3 | 掘金年度征文](https://juejin.cn/post/6908232077200588814)
[AGP Transform API 被废弃意味着什么？](https://johnsonlee.io/2021/08/02/the-deprecation-of-agp-transform-api/)


# 问题遗留
## 1、ASM二次扫描
**`（具体应该怎么做）`**：把文件扫描的操作拆分成两个部分，第一次只进行数据收集，第二次则是在第一次收集完数据的基础上再去做修改。
> 第一次扫描只进行 asm 文件访问，而不进行 asm 修改。在这个过程中只收集所需要的数据信息，这次操作也不会进行任何的 asm 替换操作和文件操作，只会将文件转化成 asm 语法相关的。这里使用 **`tree api`** 还是 **`core api`** 就随便了。
> asm 的依赖库有两种 tree api 和 core api，差别就是 tree api 基于 node 语法树，而 core api 速度会更快。
> 第二次 asm 操作的时候会进行文件 copy 操作以及类替换等等操作，第二次的时候会在第一次收集到的类数据的，之后将原来要做的类替换或者方法替换等还原出来。
```groovy
api 'org.ow2.asm:asm:9.2'
api 'org.ow2.asm:asm-tree:9.2'
```

## 2、增量缓存
路由表的增量可以解释这个情况，参考 **`DRouter`** 或者 **`Router-Android`**

当要进行第二次增量编译的时候，由于增量编译的特性，只有变更文件需要进行修改，但是这个时候之前的路由表是需要进行还原的。

**`DRouter`** 的思路是生成一个 json 文件，通过 json 文件去记录上一次的路由表，然后在增量编译的时候对这个额路由表进行修改，然后等这次编译完成之后将 json 文件进行覆盖操作。

## 3、Transform过期问题
AGP700 版本之后，**`Transform`** 已经被标识被废弃了。

**`TransformAction`** 这个是AGP700版本之后的 API。

但是 **`booster`** 和 **`bytex`** 这两个开源框架处理的逻辑是对 **`Transform`** 进行了隔离。

这么抽象的好处就是当发生了这种 AGP 的 API 过期问题，替换调整的时候，我们就可以避免所有写了 Transform 的人一起调整了。

只需要底层进行好对应的适配工作，之后让上层开发同学升级下底层库版本就行了。

## 4、AGP 7.0.0 该何去何从
* 1、AGP 新增的这几个跟 Instrumentation 相关的 API 是来自 Gradle，并不是 AGP 原创，这么一来，也就解释了为什么 AGP 的版本要与 Gradle 的版本保持一直，从4.2.2直接跳到了 7.0.0，不光是 AGP 需要 Transform，Java也需要，所以由 Gradle 来提供统一的 Transform API 也合情合理。

* 2、如果 AGP8.0 要彻底废弃 Transform API 会怎样？
> 虽然 AGP 官方没有明确给出 Transform API 的替代品，但从新增的 API 来看，方向已经很明确了，用 Gradle 提供的 TransformAction 来替代原来的 Transform API，而且 AGP 很早就已经开始使用 Gradle TransformAction 来对依赖项进行 transform 了。
> 假如使用了 Booster 的插件来说，迁移到 AGP7.0 几乎没有什么成本，毕竟 Booster 中间抽象了一层，底层是用 AGP 的 Transform API 还是 Gradle 的 TransformAction，开发者并不需要关心，能正常使用 transform 就行了。

