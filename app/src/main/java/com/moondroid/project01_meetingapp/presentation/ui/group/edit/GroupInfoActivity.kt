package com.moondroid.project01_meetingapp.presentation.ui.group.edit

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.toast
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupInfoBinding
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * 모임 정보 수정
 */
@AndroidEntryPoint
class GroupInfoActivity : BaseActivity(R.layout.activity_group_info) {
    private val binding by viewBinding(ActivityGroupInfoBinding::inflate)
    val viewModel: GroupInfoViewModel by viewModels()

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
        DMAnalyze.logEvent("GroupInfo Loaded")

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
     * 필드값 생성, 정규식 확인
     */
    fun save(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            title = binding.tvTitle.text.toString()
            location = binding.tvLocation.text.toString()
            purpose = binding.tvPurpose.text.toString()
            information = binding.tvInformation.text.toString()

            if (!title.matches(DMRegex.TITLE)) {
                toast(getString(R.string.error_title_mismatch))
                binding.tvTitle.requestFocus()
            } else if (purpose.isEmpty()) {
                toast(getString(R.string.error_purpose_empty))
                binding.tvPurpose.requestFocus()
            } else if (information.isEmpty()) {
                toast(getString(R.string.error_purpose_empty))
                binding.tvInformation.requestFocus()
            } else {
                updateGroupInfo()
            }
        } catch (e: Exception) {
            e.logException()
        }
    }

    /**
     * 모임정보 수정 요청
     */
    private fun updateGroupInfo() {
        val body = HashMap<String, RequestBody>()
        /*body["originTitle"] = originTitle.toReqBody()
        body[RequestParam.TITLE] = title.toReqBody()
        body[RequestParam.LOCATION] = location.toReqBody()
        body[RequestParam.PURPOSE] = purpose.toReqBody()
        body[RequestParam.INTEREST] = interest.toReqBody()
        body[RequestParam.INFORMATION] = information.toReqBody()*/

        var thumbPart: MultipartBody.Part? = null
        if (!thumbPath.isNullOrEmpty()) {

            val file = thumbPath?.let { File(it) }
            file?.let {
                val requestBody = it.asRequestBody("image/*".toMediaType())
                thumbPart = MultipartBody.Part.createFormData("thumb", it.name, requestBody)
            }
        }

        var imagePart: MultipartBody.Part? = null
        if (!imagePath.isNullOrEmpty()) {
            val file = imagePath?.let { File(it) }
            file?.let {
                val requestBody = it.asRequestBody("image/*".toMediaType())
                imagePart = MultipartBody.Part.createFormData("intro", it.name, requestBody)
            }
        }
        debug("updateGroupInfo() , requestBody = $body")
        viewModel.updateGroup(body, thumbPart, imagePart)
    }

    /**
     * 관심사 선택
     */
    fun toInterest() {
        val intent = Intent(this, InterestActivity::class.java)
        activityResult(intent) {
            interest = getString(it.getIntExtra(IntentParam.INTEREST, 0))
            val resId = it.getIntExtra(IntentParam.INTEREST_ICON, 0)
            Glide.with(this).load(resId).into(binding.icInterest)
        }
    }

    /**
     * 지역 선택
     */
    fun toLocation() {
        val intent = Intent(this, LocationActivity::class.java)
        activityResult(intent) {
            location = it.getStringExtra(IntentParam.LOCATION).toString()
            binding.tvLocation.text = location
        }
    }

    /**
     * 썸네일 이미지 선택
     */
    fun getThumb() {
        val intent = Intent(Intent.ACTION_PICK).setType("image/*")
        activityResult(intent) {
            val uri = it.data
            uri?.let { u ->
                thumbPath = DMUtils.getPathFromUri(this, u)
                if (!thumbPath.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(thumbPath)
                    Glide.with(this).load(bitmap).into(binding.thumb)
                }
            }
        }
    }

    /**
     * 배경 이미지 선택
     */
    fun getImage(@Suppress("UNUSED_PARAMETER") vw: View) {
        val intent = Intent(Intent.ACTION_PICK).setType("image/*")
        activityResult(intent) {
            val uri = it.data
            uri?.let { u ->
                imagePath = DMUtils.getPathFromUri(this, u)
                if (!imagePath.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(imagePath)
                    Glide.with(this).load(bitmap).into(binding.ivImage)
                }
            }
        }
    }
}