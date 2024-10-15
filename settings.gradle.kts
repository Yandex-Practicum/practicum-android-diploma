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
include(":data-network")
include(":feature-search")
include(":feature-team")
include(":common-ui")
include(":common-utils")
include(":feature-favorites")
include(":data-sp")
include(":data-db")
include(":feature-vacancy")
include(":feature-filter")
include(":common-navigate")
include(":data-cache")
