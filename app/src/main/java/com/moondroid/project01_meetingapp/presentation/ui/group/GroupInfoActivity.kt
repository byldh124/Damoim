package com.moondroid.project01_meetingapp.presentation.ui.group

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
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
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
import com.moondroid.project01_meetingapp.utils.image.ImageHelper.getPathFromUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        //주소정보 가져오기
        binding.tvLocation.setOnClickListener {
            startActivityForResult(Intent(mContext, LocationActivity::class.java)) { intent ->
                intent?.let {
                    location = it.getStringExtra(IntentParam.LOCATION).toString()
                    binding.tvLocation.text = location
                }
            }
        }

        //관심사 가져오기
        binding.icInterest.setOnClickListener {
            startActivityForResult(Intent(mContext, InterestActivity::class.java)) { intent ->
                intent?.let {
                    interest = getString(it.getIntExtra(IntentParam.INTEREST, 0))
                    val resId = it.getIntExtra(IntentParam.INTEREST_ICON, 0)
                    Glide.with(this).load(resId).into(binding.icInterest)
                }
            }
        }

        //대문 사진 가져오기
        binding.ivIntro.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            ) { intent ->
                intent?.data?.let { uri ->
                    try {
                        imagePath = getPathFromUri(this, uri)
                        val bitmap = BitmapFactory.decodeFile(imagePath)
                        Glide.with(this).load(bitmap).into(binding.ivIntro)
                    } catch (e: Exception) {
                        logException(e)
                    }
                }
            }
        }

        //썸네일 사진 가져오기
        binding.ivThumb.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
            ) { intent ->
                intent?.data?.let { uri ->
                    try {
                        thumbPath = getPathFromUri(this, uri)
                        val bitmap = BitmapFactory.decodeFile(thumbPath)
                        Glide.with(this).load(bitmap).into(binding.ivThumb)
                    } catch (e: Exception) {
                        logException(e)
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            checkField()
        }
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
            updateGroupUseCase(
                originTitle,
                title,
                location,
                purpose,
                interest,
                information,
                thumbFile,
                introFile
            )
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
}