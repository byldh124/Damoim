package com.moondroid.project01_meetingapp.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.DeleteProfileUseCase
import com.moondroid.damoim.domain.usecase.sign.ResignUseCase
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivitySettingBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.dialog.ButtonDialog
import com.moondroid.project01_meetingapp.presentation.ui.sign.SignInActivity
import com.moondroid.project01_meetingapp.utils.ProfileHelper
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

    @Inject
    lateinit var resignUseCase: ResignUseCase

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
            logout()
        }

        binding.btnResign.setOnClickListener {
            ButtonDialog.Builder(mContext).apply {
                message = getString(R.string.desc_resign)
                setPositiveButton("아니요") {}
                setNegativeButton("예") {
                    resign()
                }
            }.show()
        }
    }

    private fun resign() {
        debug("id : ${ProfileHelper.profile.id}")
        CoroutineScope(Dispatchers.Main).launch {
            resignUseCase(ProfileHelper.profile.id).collect { result ->
                result.onSuccess {
                    deleteProfileUseCase().collect { result ->
                        result.onSuccess {
                            showMessage("모임대장을 이용해주셔서 감사합니다.") {
                                val intent = Intent(mContext, SignInActivity::class.java)
                                finishAffinity()
                                startActivity(intent)
                            }
                        }
                    }
                }.onFail {
                    if (it == ResponseCode.INVALID_VALUE) {
                        showMessage("모임장은 탈퇴할수 없습니다.\n모임장을 위임후에 탈퇴해주세요.")
                    } else {
                        serverError(it)
                    }
                }.onError {
                    networkError(it)
                }
            }
        }
    }

    /**
     * 로그아웃
     */
    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            deleteProfileUseCase().collect { result ->
                result.onSuccess {
                    val intent = Intent(mContext, SignInActivity::class.java)
                    finishAffinity()
                    startActivity(intent)
                }
            }
        }
    }
}