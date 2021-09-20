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

（1）告诉gradle用哪个 Android SDK 版本编译你的应用。使用任何新版本的 API 时就需要使用对应的 level 的 Android SDK。 （2）只影响编译时的行为，不影响运行时的行为。

# 三、buildToosVersion

编译工具集的版本号。

# 四、minSdkVersion

minSdkVersion 决定了 APP 可运行的 Android SDK 版本的下限和上限，即可以安装在哪个范围的 API Level 的手机系统上。

# 五、targetSdkVersion

targetSdkVersion 是 Android 提供向前兼容的主要依据，在应用的 targetSdkVersion 没有更新之前系统不会应用最新的行为变化。

比如系统升级到 Android4.4 之后，AlarmManager API 设置时间的精确度行为发生了变化，由精确变成了非精确。假设 APP 的 targetSdkVersion 是17，那么还是会保持原来的精确行为，当 targetSdkVersion >= 19 时才会使用新特性。

假设 APP 的 targetSdkVersion 是 API 26（对应Android8.0系统），然后运行的手机系统是 Android6.0，由于 Android6.0 相对于8.0来说比较老，API 和 特性都是老的，即使 APP targetSdkVersion 指定了高版本，App在低版本的手机上还是老特性。

假设手机系统升级到某个新版本的 newVersion，可能发生的变化：（1）有新的API，老的API有可能deprecated；（2）API外观没有变化，但是内部特性发生了变化。

## 5.1 有新的API

```
# Android Oreo之前
public abstract ComponentName startService(Intent service);

#Android Oreo之后，API层面新增了startForegroundService
public abstract ComponentName startService(Intent service);
puglib abstract ComponentName startForegroundService(Intent service);

# 此时如果APP想要使用前台服务，需要如下写：
if (Build.VERSION.SDK_INT >= Build.VERSION_CODE.O) {
    startForegroundService(intent); // 因为只有Android Oreo后系统才有这个API，不然编译和运行都不通过
} else {
    startService(intent);
    // 然后再Service里startForeground(...)
}
```

## 5.2 API内部特性发生变化

targetSdkVersion的版本大小可以决定是否采用新的特性还是维持老的特性。因为在手机系统升级之后，Android SDK 在提供新特性的同时会兼容一下老特性，会根据 APP targetSdkVersion的版本号来选择使用哪个特性。只要targetSdkVersion没有变化，即使手机系统升级了，还是采用老特性。上面AlarmManager就是一个典型的例子。

```
#Android SDK中的代码形如：
if (ctx.getApplicationInfo().targetSdkVersion > Build.VERSION_CODES.KITKAT) {
    // 新特性
} else {
    // 老特性
}
```

# 六、总结

compileSdkVersion 决定了编译期间能否使用新版本的 API。targetSdkVersion 决定了运行期间使用哪种特性。