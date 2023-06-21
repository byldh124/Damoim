package com.moondroid.project01_meetingapp.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity(R.layout.activity_setting) {
    private val binding by viewBinding(ActivitySettingBinding::inflate)
    private val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            e.logException()
        }
    }

    /**
     * 로그아웃
     */
    fun logout() {
        try {
            val intent = Intent(this, SignInActivity::class.java)
            finishAffinity()
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            e.logException()
        }
    }
}