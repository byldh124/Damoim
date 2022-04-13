package com.moondroid.damoim.application

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.util.helper.Utility
import com.moondroid.damoim.R
import com.moondroid.damoim.di.appModules
import com.moondroid.damoim.di.fragmentModules
import com.moondroid.damoim.di.viewModelModules
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.model.User
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class DMApp : Application() {

    companion object {
        lateinit var user: User
        lateinit var group: GroupInfo
        lateinit var prefs: Preferences
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DMApp)
            fragmentFactory()
            modules(appModules)
            modules(fragmentModules)
            modules(viewModelModules)
        }

        //set SharedPreference
        prefs = Preferences(applicationContext)

        var keyHash = Utility.getKeyHash(this).toString()
        DMLog.e(keyHash)

        KakaoSdk.init(this, resources.getString(R.string.kakao_native_app_key))

    }


}