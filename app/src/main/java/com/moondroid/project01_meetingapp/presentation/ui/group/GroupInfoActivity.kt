package com.moondroid.project01_meetingapp.presentation.ui.group

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bumptech.glide.Glide
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.UpdateGroupUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityGroupInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
import com.moondroid.project01_meetingapp.utils.image.ImageHelper.getPathFromUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * 모임 정보 수정
 */
@AndroidEntryPoint
class GroupInfoActivity : BaseActivity() {
    private val binding by viewBinding(ActivityGroupInfoBinding::inflate)

    @Inject
    lateinit var updateGroupUseCase: UpdateGroupUseCase

    private lateinit var originTitle: String                            // 초기 그룹명
    private lateinit var title: String                                  // 변경된 그룹명
    private lateinit var location: String                               // 모임지역
    private lateinit var purpose: String                                // 모임 목적
    private lateinit var interest: String                               // 관심사
    private lateinit var thumb: String                                  // 모임 썸네일
    private lateinit var image: String                                  // 모임 배경 이미지
    private lateinit var information: String                            // 모임 설명

    private var thumbPath: String? = null                               // 썸네일 Real Path
    private var imagePath: String? = null                               // 배경이미지 Real Path

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FBAnalyze.logEvent("GroupInfo Loaded")

        binding.groupInfo = DMApp.group

        originTitle = DMApp.group.title
        title = DMApp.group.title
        location = DMApp.group.location
        purpose = DMApp.group.purpose
        interest = DMApp.group.interest
        thumb = DMApp.group.thumb
        image = DMApp.group.image
        information = DMApp.group.information

        initView()
    }

    /**
     * initialize View
     * */
    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.tvLocation.setOnClickListener {
            locationLauncher.launch(Intent(mContext, LocationActivity::class.java))
        }

        binding.icInterest.setOnClickListener {
            interestLauncher.launch(Intent(mContext, InterestActivity::class.java))
        }

        binding.ivIntro.setOnClickListener {
            introLauncher.launch(Intent(Intent.ACTION_PICK).setType("image/*"))
        }

        binding.ivThumb.setOnClickListener {
            thumbLauncher.launch(Intent(Intent.ACTION_PICK).setType("image/*"))
        }

        binding.btnSave.setOnClickListener { checkField() }
    }

    /**
     * 필드값 생성, 정규식 확인
     */
    private fun checkField() {
        try {
            title = binding.tvTitle.text.toString()
            location = binding.tvLocation.text.toString()
            purpose = binding.etPurpose.text.toString()
            information = binding.tvInformation.text.toString()

            if (!title.matches(DMRegex.TITLE)) {
                toast(getString(R.string.error_title_mismatch))
                binding.tvTitle.requestFocus()
            } else if (purpose.isEmpty()) {
                toast(getString(R.string.error_purpose_empty))
                binding.etPurpose.requestFocus()
            } else if (information.isEmpty()) {
                toast(getString(R.string.error_purpose_empty))
                binding.tvInformation.requestFocus()
            } else {
                updateGroupInfo()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 모임정보 수정 요청
     */
    private fun updateGroupInfo() {
        val thumbFile = thumbPath?.let { File(it) }
        val introFile = imagePath?.let { File(it) }

        CoroutineScope(Dispatchers.Main).launch {
            updateGroupUseCase(originTitle, title, location, purpose, interest, information, thumbFile, introFile)
                .collect { result ->
                    result.onSuccess {
                        DMApp.group = it
                        showMessage("그룹 정보를 수정했습니다.", ::setResultAndFinish)
                    }.onFail {
                        serverError(it)
                    }.onError {
                        networkError(it)
                    }
                }
        }
    }

    /**
     * 관심사 선택
     */
    private val interestLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                interest = getString(intent.getIntExtra(IntentParam.INTEREST, 0))
                val resId = intent.getIntExtra(IntentParam.INTEREST_ICON, 0)
                Glide.with(this).load(resId).into(binding.icInterest)
            }
        }
    }

    /**
     * 지역 선택
     */
    private val locationLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                location = intent.getStringExtra(IntentParam.LOCATION).toString()
                binding.tvLocation.text = location
            }
        }
    }

    /**
     * 썸네일 이미지 선택
     */
    private val thumbLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                val uri = intent.data
                uri?.let { u ->
                    thumbPath = getPathFromUri(this, u)
                    if (!thumbPath.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(thumbPath)
                        Glide.with(this).load(bitmap).into(binding.ivThumb)
                    }
                }
            }
        }
    }

    /**
     * 배경 이미지 선택
     */
    private val introLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                val uri = intent.data
                uri?.let { u ->
                    imagePath = getPathFromUri(this, u)
                    if (!imagePath.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(imagePath)
                        Glide.with(this).load(bitmap).into(binding.ivIntro)
                    }
                }
            }
        }
    }
}