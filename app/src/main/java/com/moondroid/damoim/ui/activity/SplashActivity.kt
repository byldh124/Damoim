package com.moondroid.damoim.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moondroid.damoim.R
import com.moondroid.damoim.utils.view.logException

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_splash)

        } catch (e: Exception) {
            logException(e)
        }

    }
}