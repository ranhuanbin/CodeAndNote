plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId("com.module.base_android")
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
}
