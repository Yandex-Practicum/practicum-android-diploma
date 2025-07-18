// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.8.2" apply false
    id("org.jetbrains.kotlin.android") version "2.2.0" apply false
    id("convention.detekt")
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
