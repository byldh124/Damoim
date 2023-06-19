buildscript {
    dependencies {
        classpath(libs.kotlin.gradleplugin)
    }
}

@Suppress("DSL_SCOPE_VIOLATION")
plugins{
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.android.kotlin.kapt) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.android.navigation.safeargs) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.android.kotlin.parcelize) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}