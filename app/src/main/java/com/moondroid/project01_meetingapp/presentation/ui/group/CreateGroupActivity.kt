package com.moondroid.project01_meetingapp.presentation.ui.group

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.CreateGroupUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityCreateBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.common.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.common.location.LocationActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.main.GroupActivity
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import com.moondroid.project01_meetingapp.utils.ViewExtension.afterTextChanged
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide
import com.moondroid.project01_meetingapp.utils.ViewExtension.setupToolbar
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.project01_meetingapp.utils.ViewExtension.visible
import com.moondroid.project01_meetingapp.utils.image.ImageHelper.getPathFromUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class CreateGroupActivity : BaseActivity() {
    private val binding by viewBinding(ActivityCreateBinding::inflate)
    private var path: String? = null                                // 썸네일 이미지 Real path
    private var interest: String? = null                            // 관심사
    private var location: String? = null                            // 모임 지역
    private lateinit var title: String                              // 모임명
    private lateinit var purpose: String                            // 모임 목적

    @Inject
    lateinit var createGroupUseCase: CreateGroupUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        Handler(Looper.getMainLooper()).postDelayed(::toInterest, 500)
    }

    /**
     * View 초기화
     */
    private fun initView() {
        setupToolbar(toolbar = binding.toolbar)

        binding.tvMsgLength.text =
            String.format(getString(R.string.cmn_message_length), binding.etPurpose.length())

        binding.etPurpose.afterTextChanged {
            binding.tvMsgLength.text = String.format(getString(R.string.cmn_message_length), it.length)
        }

        binding.wrThumb.setOnClickListener {
            getCroppedImage { uri ->
                path = getPathFromUri(this, uri)
                if (!path.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(path)
                    binding.ivThumb.glide(bitmap)
                    binding.tvEmptyImage.visible(false)
                }

            }
        }

        binding.icInterest.setOnClickListener {
            toInterest()
        }

        binding.tvLocation.setOnClickListener {
            startActivityForResult(Intent(mContext, LocationActivity::class.java)) { intent ->
                intent?.let {
                    location = intent.getStringExtra(IntentParam.LOCATION).toString()
                    binding.tvLocation.text = location
                }
            }
        }

        binding.btnSave.setOnClickListener {
            checkField()
        }
    }

    private fun toInterest() {
        startActivityForResult(Intent(mContext, InterestActivity::class.java)) {
            it?.let {
                interest = getString(it.getIntExtra(IntentParam.INTEREST, 0))
                val resId = it.getIntExtra(IntentParam.INTEREST_ICON, 0)
                binding.icInterest.glide(resId)
            }
        }
    }

    /**
     * 데이터 유효성 체크
     */
    private fun checkField() {
        title = binding.etTitle.text.toString()
        purpose = binding.etPurpose.text.toString()

        if (!title.matches(DMRegex.TITLE)) {
            toast(getString(R.string.error_title_mismatch))
        } else if (purpose.isEmpty()) {
            toast(getString(R.string.error_purpose_empty))
        } else if (location.isNullOrEmpty()) {
            toast(getString(R.string.error_location_empty))
        } else if (interest.isNullOrEmpty()) {
            toast(getString(R.string.error_interest_empty))
            toInterest()
        } else if (path.isNullOrEmpty()) {
            toast(getString(R.string.error_thumb_empty))
        } else {
            createGroup(location!!, interest!!, File(path!!))
        }
    }

    /**
     * 모임 생성 요청
     */
    private fun createGroup(location: String, interest: String, file: File) {
        CoroutineScope(Dispatchers.Main).launch {
            createGroupUseCase(
                ProfileHelper.profile.id,
                title,
                location,
                purpose,
                interest,
                file
            ).collect { result ->
                result.onSuccess {
                    DMApp.group = it
                    val sIntent = Intent(mContext, GroupActivity::class.java)
                    sIntent.putExtra(IntentParam.SHOW_TUTORIAL, true)
                    startActivity(sIntent)
                    file.delete()
                    finish()
                }.onFail {
                    serverError(it)
                }.onError {
                    networkError(it)
                }
            }
        }
    }
}