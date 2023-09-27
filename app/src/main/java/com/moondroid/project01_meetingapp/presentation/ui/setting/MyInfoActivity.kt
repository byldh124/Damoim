package com.moondroid.project01_meetingapp.presentation.ui.setting

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.damoim.domain.usecase.profile.UpdateProfileUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityMyInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.ViewExtension.afterTextChanged
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide
import com.moondroid.project01_meetingapp.utils.ViewExtension.init
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
import com.moondroid.project01_meetingapp.utils.image.ImageHelper.getPathFromUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * 회원 프로필 수정
 */
@AndroidEntryPoint
class MyInfoActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMyInfoBinding::inflate)
    private val viewModel: MyInfoViewModel by viewModels()
    private var path: String? = null
    private lateinit var gender: String

    @Inject
    lateinit var updateProfileUseCase: UpdateProfileUseCase

    @Inject
    lateinit var profileUseCase: ProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FBAnalyze.logEvent("MyInfo Loaded")
        binding.profile = DMApp.profile
        initView()
    }

    /**
     * View initialize
     */
    @SuppressLint("SetTextI18n")
    private fun initView() {
        with(binding) {
            thumb.glide(DMApp.profile.thumb)
            toolbar.init(this@MyInfoActivity)
            tvMsgLength.text = String.format(getString(R.string.cmn_message_length), binding.etMsg.length())
            etMsg.afterTextChanged {
                binding.tvMsgLength.text =
                    String.format(getString(R.string.cmn_message_length), it.length)
            }
            rg.setOnCheckedChangeListener { _, id ->
                gender = if (id == R.id.rbMale) {
                    this@MyInfoActivity.getString(R.string.cmn_male)
                } else {
                    this@MyInfoActivity.getString(R.string.cmn_female)
                }
            }

            confirm.setOnClickListener {
                update()
            }

            thumbWrapper.setOnClickListener {
                imageLauncher.launch(Intent(Intent.ACTION_PICK).setType("image/*"))
            }
        }
    }

    /**
     * 지역 선택
     */
    fun toLocation() {
        registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                    binding.tvLocation.text = intent.getStringExtra(IntentParam.LOCATION).toString()
                }
            }
        }.launch(
            Intent(this, LocationActivity::class.java)
                .putExtra(IntentParam.ACTIVITY, ActivityTy.MY_INFO)
        )
    }

    /**
     * 생년월일 선택
     */
    fun toBirth() {
        try {
            val array = binding.tvBirth.text.toString().split(".")
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
                this, { _, p1, p2, p3 ->
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
    private val imageLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                val uri = intent.data
                uri?.let { u ->
                    path = getPathFromUri(this, u)
                    if (!path.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(path)
                        Glide.with(this).load(bitmap).into(binding.thumb)
                    }
                }
            }
        }
    }

    fun update() {
        val thumbFile : File? = path?.let { File(it) }
        CoroutineScope(Dispatchers.Main).launch {
            updateProfileUseCase(
                id = DMApp.profile.id,
                name = binding.etName.text.toString(),
                birth = binding.tvBirth.text.toString(),
                gender = if (binding.rbMale.isChecked) "남자" else "여자",
                location = binding.tvLocation.text.toString(),
                message = binding.etMsg.text.toString(),
                thumb = thumbFile
            ).collect { result ->
                result.onSuccess {
                    showMessage("변경이 완료되었습니다", ::setResultAndFinish)
                }.onFail {

                }.onError {

                }
            }
        }
    }
}