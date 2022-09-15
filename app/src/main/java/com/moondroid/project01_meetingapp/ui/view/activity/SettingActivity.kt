package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.view.View
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override fun init() {
        binding.activity = this
        initView()
    }

    /**
     * Initialize View
     */
    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 로그아웃
     */
    fun logout(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            DMApp.prefs.clear()

            DMAnalyze.logEvent("Logout")

            val intent = Intent(this, SignInActivity::class.java)
            finishAffinity()
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}