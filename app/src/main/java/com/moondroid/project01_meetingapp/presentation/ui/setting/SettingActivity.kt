package com.moondroid.project01_meetingapp.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.project01_meetingapp.utils.ViewExtension.init
import com.moondroid.damoim.common.Extension.logException

import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.DeleteProfileUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
        binding.toolbar.init(this)
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