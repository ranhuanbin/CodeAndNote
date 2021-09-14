背景：插件运行时需要对插件 SDK Version 进行控制。

# 一、Android SDK Version Type

* 1、compileSdkVersion
* 2、buildToolsVersion
* 3、minSdkVersion
* 4、targetSdkVersion

```groovy
android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")
    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(29)
    }
}
```

# 二、compileSdkVersion
（1）告诉gradle用哪个 Android SDK 版本编译你的应用。使用任何新版本的 API 时就需要使用对应的 level 的 Android SDK。
（2）只影响编译时的行为，不影响运行时的行为。

# 三、buildToosVersion
编译工具集的版本号。

# 四、minSdkVersion
minSdkVersion 决定了 APP 可运行的 Android SDK 版本的下限和上限，即可以安装在哪个范围的 API Level 的手机系统上。