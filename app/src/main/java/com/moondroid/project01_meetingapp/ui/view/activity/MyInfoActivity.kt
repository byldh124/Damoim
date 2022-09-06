package com.moondroid.project01_meetingapp.ui.view.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.moondroid.project01_meetingapp.databinding.ActivityMyInfoBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.viewmodel.ProfileSetViewModel
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.view.*
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

import java.io.File

/**
 * 회원 프로필 수정
 */
@AndroidEntryPoint
class MyInfoActivity : BaseActivity<ActivityMyInfoBinding>(R.layout.activity_my_info) {

    private val viewModel: ProfileSetViewModel by viewModels()
    private var path: String? = null
    lateinit var user: User
    private lateinit var gender: String

    private val nameRegex =
        Regex("^(.{2,8})$")                                             // 이름 정규식     [2 - 8 글자]

    /* 관심지역 ActivityResult */
    private val getLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        binding.tvLocation.text =
                            it.getStringExtra(IntentParam.LOCATION).toString()
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    /* 프로필 이미지 ActivityResult */
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
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    override fun init() {

        DMAnalyze.logEvent("MyInfo Loaded")

        user = DMApp.user
        binding.activity = this
        initView()
        initViewModel()
    }

    /**
     * View initialize
     */
    @SuppressLint("SetTextI18n")
    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }

            binding.tvMsgLength.text =
                String.format(getString(R.string.cmn_message_length), binding.etMsg.length())
            binding.etMsg.afterTextChanged {
                binding.tvMsgLength.text =
                    String.format(getString(R.string.cmn_message_length), it.length)
            }

            binding.rg.setOnCheckedChangeListener { _, id ->
                gender = if (id == R.id.rbMale) {
                    this@MyInfoActivity.getString(R.string.cmn_male)
                } else {
                    this@MyInfoActivity.getString(R.string.cmn_female)
                }
            }

            gender = DMApp.user.gender
            if (gender == getString(R.string.cmn_female)) {
                binding.rbFemale.isChecked = true
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

        viewModel.profileResponse.observe(this) {
            try {
                log("updateProfile , observe() , Response => $it")
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        showMessage(getString(R.string.alm_profile_update_success)) {
                            DMApp.user = Gson().fromJson(it.body.asJsonObject, User::class.java)
                            DMApp.prefs.putString(
                                PrefKey.USER_INFO,
                                it.body.toString()
                            )
                            finish()
                        }
                    }

                    else -> {
                        showMessage(
                            String.format(
                                getString(R.string.error_profile_update_fail),
                                "E01 [${it.code}]"
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
     * 지역 선택
     */
    fun toLocation(@Suppress("UNUSED_PARAMETER") vw: View) {
        getLocation.launch(Intent(this, LocationActivity::class.java))
    }

    /**
     * 생년월일 선택
     */
    fun toBirth(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val array = user.birth.split(".")
            val year: Int = try {
                array[0].toInt()
            } catch (e: Exception) {
                1990
            }
            val month: Int = try {
                array[1].toInt() - 1
            } catch (e: Exception) {
                0
            }

            val date: Int = try {
                array[2].toInt()
            } catch (e: Exception) {
                1
            }

            val datePicker = DatePickerDialog(
                this,
                { _, p1, p2, p3 ->
                    binding.tvBirth.text = String.format("%d.%d.%d", p1, p2 + 1, p3)
                }, year, month, date
            )

            datePicker.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 프로필 이미지 선택
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


    /**
     * 데이터 유효성 체크, 프로필 수정 요청
     */
    fun saveProfile(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val id = DMApp.user.id
            val name = binding.etName.text.toString()
            val birth = binding.tvBirth.text.toString()
            val location = binding.tvLocation.text.toString()
            val thumb = DMApp.user.thumb
            val message = binding.etMsg.text.toString()

            if (!name.matches(nameRegex)) {
                toast(getString(R.string.error_name_mismatch))
                return
            } else if (message.isEmpty()) {
                toast(getString(R.string.error_message_empty))
                return
            }

            val body = HashMap<String, RequestBody>()
            body[RequestParam.ID] = id.toReqBody()
            body[RequestParam.NAME] = name.toReqBody()
            body[RequestParam.BIRTH] = birth.toReqBody()
            body[RequestParam.GENDER] = gender.toReqBody()
            body[RequestParam.LOCATION] = location.toReqBody()
            body[RequestParam.THUMB] = thumb.toReqBody()
            body[RequestParam.MESSAGE] = message.toReqBody()

            var filePart: MultipartBody.Part? = null
            if (!path.isNullOrEmpty()) {

                val file = path?.let { File(it) }
                file?.let {
                    val requestBody = it.asRequestBody("image/*".toMediaType())
                    filePart = MultipartBody.Part.createFormData("thumb", it.name, requestBody)
                }
            }

            viewModel.updateProfile(body, filePart)
        } catch (e: Exception) {
            logException(e)
        }
    }
}