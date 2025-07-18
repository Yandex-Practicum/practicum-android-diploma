plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0"
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "HH_ACCESS_TOKEN", value = "\"${developProperties.hhAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    implementation(libs.fragment.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // navigation libraries
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion

    // annotation processor
    ksp(libs.moshi.kotlin.codegen)

    // reactive
    implementation(libs.kotlinx.coroutines.android)

    // DI
    implementation(libs.koin.android)

    // DB
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // network
    implementation(libs.retrofit)
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    // images
    implementation(libs.glide)
}
