package com.moondroid.project01_meetingapp.presentation.ui.setting

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.afterTextChanged
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityMyInfoBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import dagger.hilt.android.AndroidEntryPoint

/**
 * 회원 프로필 수정
 */
@AndroidEntryPoint
class MyInfoActivity : BaseActivity(R.layout.activity_my_info) {

    private val binding by viewBinding(ActivityMyInfoBinding::inflate)
    private val viewModel: MyInfoViewModel by viewModels()
    private var path: String? = null
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DMAnalyze.logEvent("MyInfo Loaded")
        initView()
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
        } catch (e: Exception) {
            e.logException()
        }
    }

    /**
     * 지역 선택
     */
    fun toLocation() {
        val sendIntent = Intent(this, LocationActivity::class.java).putExtra(
            IntentParam.ACTIVITY, ActivityTy.MY_INFO
        )

        activityResult(sendIntent) {
            binding.tvLocation.text = intent.getStringExtra(IntentParam.LOCATION).toString()
        }
    }

    /**
     * 생년월일 선택
     */
    fun toBirth(){
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
            e.logException()
        }
    }

    /**
     * 프로필 이미지 선택
     */
    fun getImage() {
        try {
            val sendIntent = Intent(Intent.ACTION_PICK)
            sendIntent.type = "image/*"
            activityResult(sendIntent) {
                val uri = intent.data
                uri?.let { u ->
                    path = DMUtils.getPathFromUri(this, u)
                    if (!path.isNullOrEmpty()) {
                        val bitmap = BitmapFactory.decodeFile(path)
                        Glide.with(this).load(bitmap).into(binding.thumb)
                    }
                }
            }
        } catch (e: Exception) {
            e.logException()
        }
    }
}