plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version Versions.ksp
    id("kotlinx-serialization")
}

android {
    compileSdk = AppConfig.compileSdk
//    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId = "com.yildiz.ministockapp"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions.jvmTarget = "1.8"
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = "1.1.1"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(AppDependencies.appLibraries)
    ksp(AppDependencies.symbolicLibraries)
    testImplementation(AppDependencies.testLibraries)
    androidTestImplementation(AppDependencies.androidTestLibraries)
    debugImplementation(AppDependencies.debugLibraries)
}