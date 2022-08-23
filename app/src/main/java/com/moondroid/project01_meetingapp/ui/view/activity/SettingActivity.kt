package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override fun init() {
        binding.activity = this
    }

    /**
     * 로그아웃
     **/
    fun logout(@Suppress("UNUSED_PARAMETER") vw: View) {
        DMApp.prefs.clear()

        val intent = Intent(this, SignInActivity::class.java)
        finishAffinity()
        startActivityWithAnim(intent)
    }
}