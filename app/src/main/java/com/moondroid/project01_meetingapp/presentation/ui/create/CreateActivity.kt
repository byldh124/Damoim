package com.moondroid.project01_meetingapp.presentation.ui.create

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.Extension.afterTextChanged
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.toast
import com.moondroid.damoim.common.Extension.visible
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityCreateBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


@AndroidEntryPoint
class CreateActivity : BaseActivity(R.layout.activity_create) {
    private val binding by viewBinding(ActivityCreateBinding::inflate)
    private val viewModel: CreateViewModel by viewModels()
    private var path: String? = null                                // 썸네일 이미지 Real path
    private var interest: String? = null                            // 관심사
    private var location: String? = null                            // 모임 지역
    private lateinit var title: String                              // 모임명
    private lateinit var purpose: String                            // 모임 목적

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        initView()
        Handler(Looper.getMainLooper()).postDelayed(this::toInterest, 500)
    }

    /**
     * View 초기화
     */
    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            binding.tvMsgLength.text =
                String.format(getString(R.string.cmn_message_length), binding.etPurpose.length())
            binding.etPurpose.afterTextChanged {
                binding.tvMsgLength.text =
                    String.format(getString(R.string.cmn_message_length), it.length)
            }
        } catch (e: Exception) {
            e.logException()
        }
    }

    /**
     * 썸네일 이미지 선택
     */
    fun getImage(@Suppress("UNUSED_PARAMETER") vw: View) {
        val intent = Intent(Intent.ACTION_PICK).setType("image/*")
        activityResult(intent) {
            val uri = intent.data
            uri?.let {
                path = DMUtils.getPathFromUri(this, it)
                if (!path.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(path)
                    Glide.with(this).load(bitmap).into(binding.thumb)
                    binding.tvImage.visible(false)
                }
            }
        }
    }

    /**
     * 모임 지역 선택
     */
    fun toLocation() {
        val intent = Intent(this, LocationActivity::class.java)
            .putExtra(IntentParam.ACTIVITY, ActivityTy.CREATE)
        activityResult(intent) {
            location = intent.getStringExtra(IntentParam.LOCATION).toString()
            binding.tvLocation.text = location
        }
    }

    /**
     * 관심사 선택
     */
    fun toInterest() {
        val intent = Intent(this, InterestActivity::class.java)
        activityResult(intent) {
            interest = getString(intent.getIntExtra(IntentParam.INTEREST, 0))
            val resId = intent.getIntExtra(IntentParam.INTEREST_ICON, 0)
            Glide.with(this).load(resId).into(binding.icInterest)
        }
    }

    fun toInterest(@Suppress("UNUSED_PARAMETER") vw: View) {
        toInterest()
    }

    /**
     * 데이터 유효성 체크
     */
    fun checkField(@Suppress("UNUSED_PARAMETER") vw: View) {
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
            e.logException()
        }
    }

    /**
     * 모임 생성 요청
     */
    private fun createGroup() {
        try {
            val body = HashMap<String, RequestBody>()
            /*body[RequestParam.ID] = user!!.id.toReqBody()
            body[RequestParam.TITLE] = title.toReqBody()
            body[RequestParam.PURPOSE] = purpose.toReqBody()
            body[RequestParam.INTEREST] = interest!!.toReqBody()
            body[RequestParam.LOCATION] = location!!.toReqBody()*/

            var filePart: MultipartBody.Part? = null
            if (!path.isNullOrEmpty()) {

                val file = path?.let { File(it) }
                file?.let {
                    val requestBody = it.asRequestBody("image/*".toMediaType())
                    filePart = MultipartBody.Part.createFormData("thumb", it.name, requestBody)
                }
            }

            viewModel.createGroup(body, filePart)
        } catch (e: Exception) {
            e.logException()
            showMessage(getString(R.string.error_create_group_fail), "E02")
        }
    }
}