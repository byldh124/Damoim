package com.moondroid.project01_meetingapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.startup.AppInitializer
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.utils.Preferences
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import dagger.hilt.android.HiltAndroidApp
import net.danlew.android.joda.JodaTimeInitializer

@HiltAndroidApp
class DMApp : Application() {

    companion object {
        lateinit var group: GroupInfo
        lateinit var prefs: Preferences
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)                      //다크모드 지원 X

        //koin -> hilt migration
        /*startKoin {
            androidContext(this@DMApp)
            fragmentFactory()
            modules(appModules)
            modules(fragmentModules)
            modules(viewModelModules)
        }*/


        /* initialize SharedPreferences */
        prefs = Preferences(applicationContext)

        /* initialize kakao */
        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))

        /* initialize joda time */
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(JodaTimeInitializer::class.java)

        /* initialize firebase analytics */
        DMAnalyze.init(applicationContext)
    }
}