import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import ru.practicum.android.withVersionCatalog

plugins {
    id("io.gitlab.arturbosch.detekt")
}

fun Detekt.setupCommonDetektSettings() {
    // Common properties
    parallel = true
    autoCorrect = false
    disableDefaultRuleSets = false
    buildUponDefaultConfig = false

    // workaround for https://github.com/gradle/gradle/issues/15383
    project.withVersionCatalog { libs ->
        jvmTarget = JavaVersion.valueOf(libs.versions.java.get()).toString()
    }

    // Setup sources for run
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")

    // reports configuration
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(false)
    }
}

val detektAll by tasks.register<Detekt>("detektAll") {
    description = "Runs over whole code base without the starting overhead for each module."

    setupCommonDetektSettings()

    // Configuration
    config.setFrom(files(project.rootDir.resolve("conf/detekt.yml")))
}

val detektFormat by tasks.register<Detekt>("detektFormat") {
    description = "Reformats whole code base."

    setupCommonDetektSettings()
    autoCorrect = true

    // Configuration
    config.setFrom(files(project.rootDir.resolve("conf/detekt.yml")))
}

val detektProjectBaseline by tasks.register<DetektCreateBaselineTask>("detektProjectBaseline") {
    description = "Overrides current baseline."

    // Setup sources for run
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")

    // Common properties
    buildUponDefaultConfig.set(true)
    ignoreFailures.set(true)
    parallel.set(true)

    // workaround for https://github.com/gradle/gradle/issues/15383
    project.withVersionCatalog { libs ->
        jvmTarget = JavaVersion.valueOf(libs.versions.java.get()).toString()
    }

    // Configuration
    config.setFrom(files(project.rootDir.resolve("conf/detekt.yml")))
}

// workaround for https://github.com/gradle/gradle/issues/15383
project.withVersionCatalog { libs ->
    dependencies {
        add("detekt", libs.staticAnalysis.detektCli)
        add("detektPlugins", libs.staticAnalysis.detektFormatting)
        add("detektPlugins", libs.staticAnalysis.detektLibraries)
    }
}
