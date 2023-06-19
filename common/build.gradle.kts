@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.android.kotlin.kapt)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace ="com.moondroid.damoim.common"
    compileSdk = 33

    defaultConfig {
        minSdk = 23
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.java.get()
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.kotlin)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Glide
    implementation(libs.glide.android)
    kapt(libs.glide.compiler)

    //Gson
    implementation(libs.google.gson)

    //lifecycle
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.bundles.lifecycle)

    implementation(libs.bundles.firebase)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.androidx.appcompat)
}

kapt {
    correctErrorTypes = true
}