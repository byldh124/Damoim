package com.moondroid.project01_meetingapp.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.damoim.domain.usecase.profile.DeleteProfileUseCase
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.sign.SignInActivity
import com.moondroid.project01_meetingapp.utils.ViewExtension.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySettingBinding::inflate)
    private val viewModel: SettingViewModel by viewModels()

    @Inject
    lateinit var deleteProfileUseCase: DeleteProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        initView()
    }

    /**
     * Initialize View
     */
    private fun initView() {
        setupToolbar(binding.toolbar)
        binding.btnLogout.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                deleteProfileUseCase().collect {
                    logout()
                }
            }
        }
    }

    /**
     * 로그아웃
     */
    private fun logout() {
        val intent = Intent(this, SignInActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}