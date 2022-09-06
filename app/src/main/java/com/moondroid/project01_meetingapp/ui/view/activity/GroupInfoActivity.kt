package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityGroupInfoBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.viewmodel.GroupInfoViewModel
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.RequestParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.toReqBody
import com.moondroid.project01_meetingapp.utils.view.toast
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

    private val titleRegex = Regex("^(.{2,20})$")                // 이름 정규식 [2-20]

    /* 관심사 ActivityResult */
    private val getInterest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {

                        interest = getString(it.getIntExtra(IntentParam.INTEREST, 0))

                        val resId = it.getIntExtra(IntentParam.INTEREST_ICON, 0)

                        log("getInterest => interest : $interest , resId : $resId")

                        Glide
                            .with(this@GroupInfoActivity)
                            .load(resId)
                            .into(binding.icInterest)
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    /* 모임지역 ActivityResult */
    private val getLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        location = it.getStringExtra(IntentParam.LOCATION).toString()
                        binding.tvLocation.text = location
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    /* 썸네일 ActivityResult */
    private val getThumb =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
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
            } catch (e: Exception) {
                logException(e)
            }
        }

    /* 배경이미지 ActivityResult */
    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
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
            } catch (e: Exception) {
                logException(e)
            }
        }

    /**
     * View initialize
     */
    override fun init() {
        try {
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
    private fun initView(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
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

                        showMessage(getString(R.string.alm_modify_group_info_complete)){
                            this@GroupInfoActivity.finish()
                        }
                    }

                    ResponseCode.ALREADY_EXIST -> {
                        showMessage(this@GroupInfoActivity.getString(R.string.error_already_exist_title)) {
                            binding.title.requestFocus()
                        }
                    }

                    else -> {
                        showMessage(
                            String.format(
                                this@GroupInfoActivity.getString(R.string.error_update_group_info),
                                "E1 : ${it.code}"
                            )
                        )
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

            if (!title.matches(titleRegex)) {
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
        getInterest.launch(Intent(this, InterestActivity::class.java))
    }

    /**
     * 지역 선택
     */
    fun toLocation(@Suppress("UNUSED_PARAMETER") vw: View) {
        getLocation.launch(Intent(this, LocationActivity::class.java))
    }

    /**
     * 썸네일 이미지 선택
     */
    fun getThumb(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getThumb.launch(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 배경 이미지 선택
     */
    fun getImage(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getImage.launch(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}