plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    id("ru.practicum.android.diploma.plugins.developproperties")
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ksp { arg("room.schemaLocation", "$projectDir/schemas") }

        buildConfigField(type = "String", name = "HH_ACCESS_TOKEN", value = "\"${developProperties.hhAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    java { toolchain.languageVersion.set(JavaLanguageVersion.of(18)) }
    kotlinOptions { jvmTarget = "18" }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.bundles.navigation.component)
    implementation(libs.bundles.serialization)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation(libs.bundles.retrofit2)
    implementation(libs.bundles.room)
    implementation(libs.dagger)
    implementation(libs.glide)
    kapt(libs.dagger.compiler)
    ksp(libs.glide.compiler)
    ksp(libs.room.compiler)

    // Test
    implementation(libs.junit)
    implementation(libs.androidx.junit)
    implementation(libs.test.espresso.core)
 
    // LeakCanary
    //debugImplementation 'com.squareup.leakcanary:leakcanary-android:2.8.1'
}
