# Transform 与 Task 的关系涉及到以下问题
* 1、自定义 Transform 和其他系统 Transform 执行的顺序
* 2、Transform 和 Task 的关系
* 3、Transform 是如何被执行的

# 一、什么是 Task
> 一个 Task 代表一个构建工作的原子操作，例如编译 classes 或者生成 javadoc。

Gradle中，每一个待编译的工程都叫一个 Project。每一个 Project 在构建的时候都包含一系列的Task。比如一个 Android APK 的编译可能包含：Java 源码编译 Task，资源编译 Task，JNI 编译 Task，lint 检查 Task、打包生成 APK 的TASK、签名 TASK等。插件本身就是包含了若干 Task。

简单的说，我们的项目编译以 assembleDebug 为例子，会顺序执行非常多的 gradle task 任务，举个例子比如说 aapt，javac，**`kotlinc`** 等等，他们都是作为一个 task 存在的。

# 二、AGP 中的 Transform
```groovy
AppExtension appExtension = project.getExtensions().getByType(AppExtension.class)
appExtension.registerTransform(new DoubleTabTransform(project))
```
当我们在编写一个包含 **`Transform`** 的 **`plugin`** 插件的时候，只是对安卓的 **`AppExtension`** 注册了一个 **`Transform`**，那么Transform的本质到底是什么？

## 先说结论
* 1、一个 Transform 就是一个 Task。
* 2、Transform 的执行顺序就是 Task 的执行顺序。
* 3、通过 registerTransform 方式添加的 Transform，位于部分系统 Transform 之后，位于部分系统 Transform 之前（**`这就是一句废话`**）

## 源码分析流程
```
1、AppExtension.registerTransform
2、_transforms。add(Transform)
3、LibraryTaskManager.doCreateTasksForVariant
4、TransformManager.addTransform
5、TaskFactoryImpl.register(new TransformTask(transform))
```
**结合源码流程，得出上述结论，关于自定义 Transform 与系统 Transform 的关系，整理LibraryTaskManager.doCreateTasksForVariant：**
```
1、ExtractDeepLinksTask
2、GenerateEmptyResourceFilesTask
3、CheckManifest
4、ProcessLibraryManifest
5、createMergeResourcesTasks
6、createCompileLibraryResourcesTask
7、createShaderTask
8、createMergeAssetsTask
9、createLibraryAssetsTask
10、createBuildConfigTask
11、createProcessResTask
12、registerLibraryRClassTransformStream
13、createProcessJavaResTask
14、createAidlTask
15、createDataBindingTasksIfNecessary
16、createMlkitTask
17、javacTask
18、List<Transform> customTransforms = extension.getTransforms()
19、TransformTask（自定义的 Transform）
```
