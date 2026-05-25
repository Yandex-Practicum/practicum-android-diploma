// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    id("androidx.navigation.safeargs.kotlin") version libs.versions.navigationFragmentKtx apply false

    id("convention.detekt")
}
