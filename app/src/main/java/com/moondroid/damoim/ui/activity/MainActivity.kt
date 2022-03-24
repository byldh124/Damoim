package com.moondroid.damoim.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moondroid.damoim.R
import com.moondroid.damoim.utils.view.logException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main)
        } catch (e : Exception){
            logException(e)
        }
    }
}