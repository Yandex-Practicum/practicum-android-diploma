plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("androidx.navigation.safeargs.kotlin")
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
}

dependencies {
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)

    // UI layer libraries
    implementation(libs.ui.material)
    implementation(libs.ui.constraintLayout)

    // region Unit tests
    testImplementation(libs.unitTests.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)
    // endregion

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${libs.versions.retrofitVersion.get()}")
    implementation("com.squareup.retrofit2:converter-gson:${libs.versions.retrofitVersion.get()}")

    // Koin
    implementation("io.insert-koin:koin-android:${libs.versions.koinVersion.get()}")

    // Glide
    implementation("com.github.bumptech.glide:glide:${libs.versions.glideVersion.get()}")
    annotationProcessor("com.github.bumptech.glide:compiler:${libs.versions.glideVersion.get()}")

    // Room
    implementation("androidx.room:room-runtime:${libs.versions.roomVersion.get()}")
    kapt("androidx.room:room-compiler:${libs.versions.roomVersion.get()}")
    implementation("androidx.room:room-ktx:${libs.versions.roomVersion.get()}")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${libs.versions.navigationVersion.get()}")
    implementation("androidx.navigation:navigation-ui-ktx:${libs.versions.navigationVersion.get()}")
    implementation("androidx.fragment:fragment-ktx:${libs.versions.fragmentKtxVersion.get()}")
    implementation("androidx.viewpager2:viewpager2:${libs.versions.viewPagerVersion.get()}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${libs.versions.coroutinesVersion.get()}")
}
