package com.moondroid.project01_meetingapp.presentation.view.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupInfoBinding
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.presentation.view.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.view.location.LocationActivity
import com.moondroid.project01_meetingapp.presentation.viewmodel.GroupInfoViewModel
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.log
import com.moondroid.project01_meetingapp.utils.logException
import com.moondroid.project01_meetingapp.utils.toReqBody
import com.moondroid.project01_meetingapp.utils.toast
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
class GroupInfoActivity : BaseActivity<ActivityGroupInfoBinding>(R.layout.activity_group_info) {
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

    /**
     * View initialize
     */
    override fun init() {
        try {
            DMAnalyze.logEvent("GroupInfo Loaded")

            binding.activity = this
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
            initViewModel()
        } catch (e: Exception) {
            logException(e)
        }
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
            logException(e)
        }
    }

    /**
     * Observe ViewModel
     */
    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.groupInfoResponse.observe(this) {
            try {
                log("updateGroup() , Response => $it")
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val json = it.body.asJsonObject
                        val group = Gson().fromJson(json, GroupInfo::class.java)
                        DMApp.group = group

                        showMessage(getString(R.string.alm_modify_group_info_complete)) {
                            this@GroupInfoActivity.finish()
                        }
                    }

                    ResponseCode.ALREADY_EXIST -> {
                        showMessage(this@GroupInfoActivity.getString(R.string.error_already_exist_title)) {
                            binding.title.requestFocus()
                        }
                    }

                    else -> {
                        showMessage(getString(R.string.error_update_group_info), "E1 : ${it.code}")
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
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

            if (!title.matches(Regex.TITLE)) {
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
            logException(e)
        }
    }

    /**
     * 모임정보 수정 요청
     */
    private fun updateGroupInfo() {
        try {
            val body = HashMap<String, RequestBody>()
            body["originTitle"] = originTitle.toReqBody()
            body[RequestParam.TITLE] = title.toReqBody()
            body[RequestParam.LOCATION] = location.toReqBody()
            body[RequestParam.PURPOSE] = purpose.toReqBody()
            body[RequestParam.INTEREST] = interest.toReqBody()
            body[RequestParam.INFORMATION] = information.toReqBody()

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

            log("updateGroupInfo() , requestBody = $body")

            viewModel.updateGroup(body, thumbPart, imagePart)

        } catch (e: Exception) {
            logException(e)
        }

    }

    /**
     * 관심사 선택
     */
    fun toInterest(@Suppress("UNUSED_PARAMETER") vw: View) {
        val onResult: (Intent) -> Unit = {
            try {
                interest = getString(it.getIntExtra(IntentParam.INTEREST, 0))
                val resId = it.getIntExtra(IntentParam.INTEREST_ICON, 0)
                Glide.with(this).load(resId).into(binding.icInterest)
            } catch (e: Exception) {
                logException(e)
            }
        }
        val intent = Intent(this, InterestActivity::class.java)
        activityResult(onResult, intent)
    }

    /**
     * 지역 선택
     */
    fun toLocation(@Suppress("UNUSED_PARAMETER") vw: View) {
        val onResult: (Intent) -> Unit = {
            try {
                location = it.getStringExtra(IntentParam.LOCATION).toString()
                binding.tvLocation.text = location
            } catch (e: Exception) {
                logException(e)
            }
        }

        val intent = Intent(this, LocationActivity::class.java)
        activityResult(onResult, intent)
    }

    /**
     * 썸네일 이미지 선택
     */
    fun getThumb(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val onResult: (Intent) -> Unit = {
                val uri = it.data
                uri?.let { u ->
                    thumbPath = DMUtils.getPathFromUri(this, u)
                    if (!thumbPath.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(thumbPath)
                        Glide.with(this).load(bitmap).into(binding.thumb)
                    }
                }
            }
            val intent = Intent(Intent.ACTION_PICK).setType("image/*")

            activityResult(onResult, intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 배경 이미지 선택
     */
    fun getImage(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val onResult: (Intent) -> Unit = {
                val uri = it.data
                uri?.let { u ->
                    imagePath = DMUtils.getPathFromUri(this, u)
                    if (!imagePath.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(imagePath)
                        Glide.with(this).load(bitmap).into(binding.ivImage)
                    }
                }
            }
            val intent = Intent(Intent.ACTION_PICK).setType("image/*")

            activityResult(onResult, intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}