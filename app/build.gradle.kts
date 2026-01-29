plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.navigation.safe.args)
    id("kotlin-parcelize")
    id("ru.practicum.android.diploma.plugins.developproperties")
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

        // Токен из develop.properties (локально)
        buildConfigField(
            type = "String",
            name = "API_ACCESS_TOKEN",
            value = "\"${developProperties.apiAccessToken}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    implementation(libs.androidx.fragment.ktx)

    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // Retrofit и сеть
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Room (ksp вместо kapt)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    // Koin
    implementation(libs.koin.android)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Gson
    implementation(libs.gson)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion
}
