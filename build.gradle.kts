import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("convention.detekt")
}
allprojects {
    configurations.all {
        resolutionStrategy {
            force("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
            force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.0")
            force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")
            force("org.jetbrains.kotlin:kotlin-stdlib-common:1.9.0")
        }
    }
}
val developPropertiesFile = rootProject.file("develop.properties")

val developProperties: java.util.Properties by lazy {
    java.util.Properties().apply {
        if (developPropertiesFile.exists()) {
            developPropertiesFile.inputStream().use { load(it) }
        } else {
            println("⚠ develop.properties not found! Using fallback values.")
        }
    }
}
extra["developProperties"] = developProperties
