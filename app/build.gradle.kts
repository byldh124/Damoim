@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.android.navigation.safeargs)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.android.kotlin.kapt)
}

android {
    namespace = "com.moondroid.project01_meetingapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.moondroid.project01_meetingapp"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = true
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    lint {
        disable += "SpUsage" + "ContentDescription" + "UseCompoundDrawables" + "RtlSymmetry" + "HardcodedText"
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.espresso)

    implementation(libs.circle.imageview)

    //EncryptedSharedPreference
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    //Lottie
    implementation(libs.lottie.animation)

    // Glide
    implementation(libs.glide.android)
    kapt(libs.glide.compiler)

    // Gson
    implementation(libs.google.gson)

    // Lifecycle
    kapt(libs.androidx.lifecycle.compiler)
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.firebase)

    // Google - Login
    implementation(libs.google.sign)

    // Navigation
    implementation(libs.bundles.navigation)

    /** Kakao **/
    implementation(libs.kakao.all) // 전체 모듈 설치, 2.11.0 버전부터 지원

    /** Naver **/
    implementation(libs.naver.map)

    implementation("com.google.android.gms:play-services-location:21.0.1")

    //Clean Architecture
    implementation((project(":common")))
    implementation((project(":data")))
    implementation((project(":domain")))
}

kapt {
    correctErrorTypes = true
}