package com.moondroid.damoim.application

import android.app.Application
import com.moondroid.damoim.di.appModules
import com.moondroid.damoim.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class DMApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DMApplication)
            fragmentFactory()
            modules(appModules)
            modules(viewModelModules)
        }
    }
}