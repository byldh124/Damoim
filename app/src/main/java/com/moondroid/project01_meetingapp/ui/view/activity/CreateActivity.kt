package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityCreateBinding
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.ui.viewmodel.CreateViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.view.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class CreateActivity : BaseActivity<ActivityCreateBinding>(R.layout.activity_create) {
    private val viewModel: CreateViewModel by viewModel()
    private var path: String? = null
    private var interest: String? = null
    private var location: String? = null
    private lateinit var title: String
    private lateinit var purpose: String

    private val titleRegex =
        Regex("^(.{2,8})$")                                             // 이름 정규식     [2 글자 이상]

    private val getInterest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {

                        interest = getString(it.getIntExtra(Constants.IntentParam.INTEREST, 0))

                        val resId = it.getIntExtra(Constants.IntentParam.INTEREST_ICON, 0)

                        log("[CreateActivity] , getInterest => interest : $interest , resId : $resId")

                        Glide
                            .with(this@CreateActivity)
                            .load(resId)
                            .into(binding.icInterest)
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    override fun init() {
        binding.activity = this
        initView()
        initViewModel()
        Handler(Looper.getMainLooper()).postDelayed(this::toInterest, 500)
    }

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
            logException(e)
        }
    }

    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.createResponse.observe(this) {

            log("[CreateActivity], createResponse : $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    DMApp.group = Gson().fromJson(it.body, GroupInfo::class.java)
                    val intent = Intent(this@CreateActivity, GroupActivity::class.java)
                    intent.putExtra(Constants.ACTIVITY_TY, Constants.ActivityTy.CREATE)
                    startActivityWithAnim(intent)
                    finish()
                }

                Constants.ResponseCode.ALREADY_EXIST -> {
                    showError(getString(R.string.error_already_exist_title)) {
                        binding.etTitle.requestFocus()
                    }

                }

                else -> {
                    showError(
                        String.format(
                            getString(R.string.error_create_group_fail),
                            "E01 : ${it.code}"
                        )
                    )
                }
            }
        }
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        val uri = it.data
                        uri?.let { u ->
                            path = DMUtils.getPathFromUri(this, u)
                            if (!path.isNullOrEmpty()) {
                                val bitmap = BitmapFactory.decodeFile(path)
                                Glide.with(this).load(bitmap).into(binding.thumb)
                                binding.tvImage.gone(true)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    fun getImage(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            getImage.launch(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun toLocation(@Suppress("UNUSED_PARAMETER") vw: View) {
        getLocation.launch(Intent(this, LocationActivity::class.java))
    }

    private val getLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        location = it.getStringExtra(Constants.IntentParam.LOCATION).toString()
                        binding.tvLocation.text = location
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    /**
     * 관심사 선택 화면 전환
     **/
    fun toInterest() {
        getInterest.launch(Intent(this, InterestActivity::class.java))
    }

    fun toInterest(@Suppress("UNUSED_PARAMETER") vw: View) {
        toInterest()
    }

    fun checkField(@Suppress("UNUSED_PARAMETER") vw: View) {
        title = binding.etTitle.text.toString()
        purpose = binding.etPurpose.text.toString()

        if (!title.matches(titleRegex)) {
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
    }

    private fun createGroup() {
        try {
            val body = HashMap<String, RequestBody>()
            body[Constants.RequestParam.ID] = DMApp.user.id.toReqBody()
            body[Constants.RequestParam.TITLE] = title.toReqBody()
            body[Constants.RequestParam.PURPOSE] = purpose.toReqBody()
            body[Constants.RequestParam.INTEREST] = interest!!.toReqBody()
            body[Constants.RequestParam.LOCATION] = location!!.toReqBody()

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
            logException(e)
            showError(String.format(getString(R.string.error_create_group_fail), "E02"))
        }
    }
}