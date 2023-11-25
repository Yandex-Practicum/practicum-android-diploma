plugins {
    `kotlin-dsl`
}

group = "ru.practicum.android.buildlogic"

dependencies {
    implementation(projects.gradleExt)

    implementation(libs.staticAnalysis.detektPlugin)
    // workaround for https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}