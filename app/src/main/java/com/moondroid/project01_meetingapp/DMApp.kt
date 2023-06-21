package com.moondroid.project01_meetingapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.utils.Preferences
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DMApp : Application() {

    companion object {
        lateinit var group: GroupItem
        lateinit var prefs: Preferences
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)                      //다크모드 지원 X

        /* initialize SharedPreferences */
        prefs = Preferences(applicationContext)

        /* initialize kakao */
        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))

        /* initialize firebase analytics */
        DMAnalyze.init(applicationContext)
    }
}