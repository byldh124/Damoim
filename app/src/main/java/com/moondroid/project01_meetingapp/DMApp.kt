package com.moondroid.project01_meetingapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.damoim.common.Preferences
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DMApp : Application() {

    companion object {
        lateinit var profile: Profile
        lateinit var group: GroupItem

        fun setProfile() = ::profile.isInitialized
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)                      //다크모드 지원 X

        /* initialize kakao */
        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))

        // initialize Preferences
        Preferences.init(applicationContext)

        /* initialize firebase analytics */
        FBAnalyze.init(applicationContext)
    }
}