// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("convention.detekt")
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
}

buildscript {
    dependencies {
        classpath(libs.navigation.safe.args.gradle.plugin)
    }
}
