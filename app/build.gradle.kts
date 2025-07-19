plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("kotlin-kapt")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }

    viewBinding {
        enable = true
    }

}

dependencies {
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    implementation(libs.androidx.lifecycle)

    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // Kotlin
    implementation(libs.kotlin.stdlib)

    // Fragments
    implementation(libs.fragment.ktx)

    // Navigation Component
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Network (Retrofit + OkHttp)
    implementation(libs.bundles.network)

    // Glide (Image Loading)
    implementation(libs.glide)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.glide.compiler)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Koin (DI)
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    // Room (DataBase)
    implementation(libs.bundles.room)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion
}
