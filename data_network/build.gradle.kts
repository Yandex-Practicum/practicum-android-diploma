import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.practicum.android.diploma.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        val properties = Properties()
        properties.load(InputStreamReader(FileInputStream(File("develop.properties"))))
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "HH_ACCESS_TOKEN", "\"${properties.getProperty("hhAccessToken")}\"")
        }
        debug {
            buildConfigField("String", "HH_ACCESS_TOKEN", "\"${properties.getProperty("hhAccessToken")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    implementation(libs.ui.material)
    testImplementation(libs.unitTests.junit)
    androidTestImplementation(libs.uiTests.junitExt)
    androidTestImplementation(libs.uiTests.espressoCore)

    // Add lib
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.koin.android)
    implementation(libs.androidX.core)
    implementation(libs.androidX.appCompat)
    implementation(libs.ui.material)
    implementation(libs.retrofit)
    implementation(libs.koin.android)
    implementation(libs.converter.gson)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // modules
    implementation(project(":common_utils"))
}
