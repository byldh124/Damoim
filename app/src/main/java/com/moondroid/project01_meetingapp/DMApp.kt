package com.moondroid.project01_meetingapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.damoim.common.Preferences
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
import com.naver.maps.map.NaverMapSdk
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
        //다크모드 지원 X
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Firebase Analytics 초기화
        FBAnalyze.init(applicationContext)

        // 카카오 sdk 초기화
        KakaoSdk.init(this, getString(R.string.kakao_client_id))

        // 네이버 맵 클라이언트 초기화
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_client_id))

        // SharedPreferences 초기화
        Preferences.init(applicationContext)
    }
}