plugins {
    `kotlin-dsl`
}

group = "ru.practicum.android.diploma.plugins"

gradlePlugin {
    plugins.register("developPropertiesPlugin") {
        id = "ru.practicum.android.diploma.plugins.developproperties"
        implementationClass = "ru.practicum.android.diploma.plugins.developproperties.DevelopPropertiesPlugin"
    }
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
}
