buildscript {
    dependencies {
        classpath(AppDependencies.hiltGradlePlugin)
    }
}

plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}