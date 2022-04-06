package com.moondroid.damoim.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.moondroid.damoim.R
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.view.logException

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_splash)
            goToHomActivity(Constants.ActivityTy.SPLASH)
            finish()
        } catch (e: Exception) {
            logException(e)
        }
    }

}