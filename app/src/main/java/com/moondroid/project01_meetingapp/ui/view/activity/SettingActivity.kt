package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.view.View
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override fun init() {
        binding.activity = this
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    /**
     * 로그아웃
     */
    fun logout(@Suppress("UNUSED_PARAMETER") vw: View) {
        DMApp.prefs.clear()

        DMAnalyze.logEvent("Logout")

        val intent = Intent(this, SignInActivity::class.java)
        finishAffinity()
        startActivityWithAnim(intent)
    }
}