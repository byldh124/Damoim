@Suppress("DSL_SCOPE_VIOLATION")
        plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.android.kotlin.parcelize)
    alias(libs.plugins.android.kotlin.kapt)
}

android {
    namespace = "com.moondroid.damoim.domain"
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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.bundles.kotlin)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Gson
    implementation(libs.google.gson)

    // clean architecture : project struct
    implementation((project(":common")))
}

kapt {
    correctErrorTypes = true
}