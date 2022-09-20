package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.ui.viewmodel.SettingViewModel
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    val viewModel: SettingViewModel by viewModels()

    override fun init() {
        binding.activity = this
        initView()
        initViewModel()
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
     * Observe ViewModel
     */
    fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }
    }

    /**
     * 로그아웃
     */
    fun logout(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            DMAnalyze.logEvent("Logout")
            CoroutineScope(Dispatchers.IO).launch {
                userDao.delete(user!!)
            }
            val intent = Intent(this, SignInActivity::class.java)
            finishAffinity()
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}