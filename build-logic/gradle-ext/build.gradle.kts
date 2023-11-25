plugins {
    `kotlin-dsl`
}

group = "ru.practicum.android.buildlogic"

dependencies {
    // workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}