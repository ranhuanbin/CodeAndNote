plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("com.plugin.lib")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId("com.demo.plugin2")
        minSdkVersion(16)
        targetSdkVersion(29)
        versionCode(1)
        versionName("1.0")
        multiDexEnabled = true
    }
    compileOptions {
        targetCompatibility(1.8)
        sourceCompatibility(1.8)
    }
    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation("androidx.appcompat:appcompat:1.3.1")
//    debugImplementation "io.github.didi.dokit:dokitx:3.5.0-beta01"
}
