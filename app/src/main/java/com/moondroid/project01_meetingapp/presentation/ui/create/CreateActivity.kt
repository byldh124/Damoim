package com.moondroid.project01_meetingapp.presentation.ui.create

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.usecase.group.CreateGroupUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityCreateBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.ViewExtension.afterTextChanged
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide
import com.moondroid.project01_meetingapp.utils.ViewExtension.init
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
class CreateActivity : BaseActivity() {
    private val binding by viewBinding(ActivityCreateBinding::inflate)
    private val viewModel: CreateViewModel by viewModels()
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
        binding.toolbar.init(mContext)

        binding.tvMsgLength.text =
            String.format(getString(R.string.cmn_message_length), binding.etPurpose.length())

        binding.etPurpose.afterTextChanged {
            binding.tvMsgLength.text = String.format(getString(R.string.cmn_message_length), it.length)
        }

        binding.wrThumb.setOnClickListener {
            imageLauncher.launch(Intent(Intent.ACTION_PICK).setType("image/*"))
        }

        binding.icInterest.setOnClickListener {
            toInterest()
        }

        binding.tvLocation.setOnClickListener {
            locationLauncher.launch(Intent(mContext, LocationActivity::class.java))
        }
    }

    private fun toInterest() {
        interestLauncher.launch(Intent(mContext, InterestActivity::class.java))
    }

    private val imageLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                path = getPathFromUri(this, it)
                if (!path.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(path)
                    binding.thumb.glide(bitmap)
                    binding.tvImage.visible(false)
                }
            }
        }
    }

    private val locationLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                location = intent.getStringExtra(IntentParam.LOCATION).toString()
                binding.tvLocation.text = location
            }
        }
    }

    private val interestLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                interest = getString(intent.getIntExtra(IntentParam.INTEREST, 0))
                val resId = intent.getIntExtra(IntentParam.INTEREST_ICON, 0)
                binding.icInterest.glide(resId)
            }
        }
    }

    /**
     * 데이터 유효성 체크
     */
    fun checkField() {
        try {
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
                createGroup()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 모임 생성 요청
     */
    private fun createGroup() {
        try {
            val file = path?.let { File(it) }

            CoroutineScope(Dispatchers.Main).launch {
                createGroupUseCase(DMApp.profile.id, title, location!!, purpose, interest!!, file!!)
            }

        } catch (e: Exception) {
            logException(e)
            showMessage(getString(R.string.error_create_group_fail), "E02")
        }
    }
}