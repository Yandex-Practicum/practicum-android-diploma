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
include(":data_network")
include(":search")
include(":team")
include(":common_ui")
include(":common_utils")
include(":favorites")
include(":data_sp")
include(":data_db")
