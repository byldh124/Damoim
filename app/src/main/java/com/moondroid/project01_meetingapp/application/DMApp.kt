package com.moondroid.project01_meetingapp.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.utils.Preferences
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

@HiltAndroidApp
class DMApp : Application() {

    companion object {
        lateinit var user: User
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

        //set SharedPreference
        prefs = Preferences(applicationContext)

        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))
    }


}