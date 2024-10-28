import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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

val properties = gradleLocalProperties(rootDir, providers)
val naverClientId: String = properties.getProperty("naver.client.id")
val kakaoClientId: String = properties.getProperty("kakao.client.id")


android {
    namespace = "com.moondroid.project01_meetingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moondroid.project01_meetingapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 32
        versionName = "2.1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["naverClientId"] = naverClientId
        manifestPlaceholders["kakaoClientId"] = kakaoClientId
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            resValue("string", "naver_client_id", naverClientId)
            resValue("string", "kakao_client_id", kakaoClientId)
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "naver_client_id", naverClientId)
            resValue("string", "kakao_client_id", kakaoClientId)
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }

    lint {
        disable += "SpUsage" + "ContentDescription" + "UseCompoundDrawables" + "RtlSymmetry" + "HardcodedText"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.google.material)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.junit.espresso)

    implementation(libs.circle.imageview)

    //Lottie
    implementation(libs.lottie.animation)

    // Glide
    implementation(libs.glide.android)
    kapt(libs.glide.compiler)

    // Gson
    implementation(libs.google.gson)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.firebase)

    // Google - Login
    implementation(libs.bundles.google.auth)

    // Navigation
    // implementation(libs.bundles.navigation)

    /** Kakao **/
    implementation(libs.kakao.all) // 전체 모듈 설치, 2.11.0 버전부터 지원

    /** Naver **/
    implementation(libs.naver.map)

    implementation(libs.play.services.location)

    implementation(libs.image.cropper)

    //Clean Architecture
    implementation((project(":common")))
    implementation((project(":data")))
    implementation((project(":domain")))
}

kapt {
    correctErrorTypes = true
}