enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Practicum-Android-Diploma"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

includeBuild("build-logic")

include(":app")
include(":data")
include(":job_search")
include(":featured_jobs")
include(":team")
include(":common_ui")
include(":common_utils")
