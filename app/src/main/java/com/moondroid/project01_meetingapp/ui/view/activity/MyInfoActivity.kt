package com.moondroid.project01_meetingapp.ui.view.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityMyInfoBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.viewmodel.MyInfoViewModel
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.view.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private val viewModel: MyInfoViewModel by viewModels()
    private var path: String? = null
    private lateinit var gender: String

    override fun init() {
        DMAnalyze.logEvent("MyInfo Loaded")

        binding.activity = this
        binding.user = user
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

            gender = user!!.gender
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
                        user = Gson().fromJson(it.body.asJsonObject, User::class.java)
                        showMessage(getString(R.string.alm_profile_update_success)) { finish() }
                        CoroutineScope(Dispatchers.IO).launch {
                            userDao.insertData(user!!)
                        }
                    }

                    else -> {
                        showMessage(
                            getString(R.string.error_profile_update_fail), "E01 [${it.code}]"
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
        val onResult: (Intent) -> Unit = { intent ->
            binding.tvLocation.text =
                intent.getStringExtra(IntentParam.LOCATION).toString()
        }

        val sendIntent = Intent(this, LocationActivity::class.java)
            .putExtra(IntentParam.ACTIVITY, ActivityTy.MY_INFO)

        activityResult(onResult, sendIntent)
    }

    /**
     * 생년월일 선택
     */
    fun toBirth(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val array = user!!.birth.split(".")
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
            val onResult: (Intent) -> Unit = { intent ->
                val uri = intent.data
                uri?.let { u ->
                    path = DMUtils.getPathFromUri(this, u)
                    if (!path.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(path)
                        Glide.with(this).load(bitmap).into(binding.thumb)
                    }
                }
            }

            val sendIntent = Intent(Intent.ACTION_PICK)
            sendIntent.type = "image/*"

            activityResult(onResult, sendIntent)
        } catch (e: Exception) {
            logException(e)
        }
    }


    /**
     * 데이터 유효성 체크, 프로필 수정 요청
     */
    fun saveProfile(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val id = user!!.id
            val name = binding.etName.text.toString()
            val birth = binding.tvBirth.text.toString()
            val location = binding.tvLocation.text.toString()
            val thumb = user!!.thumb
            val message = binding.etMsg.text.toString()

            if (!name.matches(Regex.NAME)) {
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