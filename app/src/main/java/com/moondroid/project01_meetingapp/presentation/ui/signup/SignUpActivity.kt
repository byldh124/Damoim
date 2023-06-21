package com.moondroid.project01_meetingapp.presentation.ui.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Extension.init
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.repeatOnStarted
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySignUpBinding
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * 회원 가입
 *
 * 1. 기입 정보 유효성 체크
 * 2. 해시 비밀번호 생성
 * 3. 회원가입
 */
@AndroidEntryPoint
class SignUpActivity : BaseActivity(R.layout.activity_sign_up) {
    private val binding by viewBinding(ActivitySignUpBinding::inflate)

    private val viewModel: SignUpViewModel by viewModels()

    private var year = 1990
    private var month = 0
    private var date = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
        binding.model = viewModel
        initView()
        checkSocial()
    }

    /**
     * Initialize View
     */
    private fun initView() {
        try {
            with(binding) {
                toolbar.init(this@SignUpActivity)
                rgAccount.setOnCheckedChangeListener { group, checkedId ->
                    try {
                        val rb = group.findViewById<RadioButton>(checkedId)
                        viewModel.gender.value = rb.text.toString()
                    } catch (e: Exception) {
                        e.logException()
                    }
                }
                tvBirth.setOnClickListener {
                    showDateDialog()
                }

                tvLocation.setOnClickListener {
                    toLocation()
                }

                tvInterest.setOnClickListener {
                    toInterest()
                }

                tvUseTerm.setOnClickListener {
                    showUseTerm()
                }

                tvPrivacy.setOnClickListener {
                    showPrivacy()
                }
            }
        } catch (e: Exception) {
            e.logException()
        }
    }

    private fun checkSocial() {
        //카카오 미등록 시 회원가입 처리
        val fromSocial = intent.hasExtra(IntentParam.ID) &&
                intent.hasExtra(IntentParam.NAME) &&
                intent.hasExtra(IntentParam.THUMB)

        viewModel.fromSocial = fromSocial

        if (fromSocial) {
            viewModel.id.value = intent.getStringExtra(IntentParam.ID)
            viewModel.pw.value = intent.getStringExtra(IntentParam.ID)
            viewModel.pw2.value = intent.getStringExtra(IntentParam.ID)
            viewModel.name.value = intent.getStringExtra(IntentParam.NAME)
            viewModel.thumb.value = intent.getStringExtra(IntentParam.THUMB)
        }

        viewModel.gender.value = binding.rbAccountMale.text.toString()
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Loading -> {
                showLoading(event.show)
            }

            is Event.Message -> {
                showMessage(event.message)
            }

            Event.Home -> goToHomeActivity(ActivityTy.SIGN_IN)
        }
    }

    /**
     * Android 달력 다이얼로그 호출
     */
    private fun showDateDialog() {
        try {
            val datePicker = DatePickerDialog(
                this,
                R.style.DatePickerSpin,
                { _, p1, p2, p3 ->
                    year = p1
                    month = p2
                    date = p3
                    viewModel.birth.value = String.format(Locale.KOREA, "%d.%d.%d", year, month + 1, date)
                }, year, month, date
            )
            datePicker.show()
        } catch (e: Exception) {
            e.logException()
        }
    }

    /**
     * 관심지역 선택 화면 전환
     */
    private fun toLocation() {
        val intent = Intent(this, LocationActivity::class.java)
        activityResult(intent) {
            viewModel.location.setValue(intent.getStringExtra(IntentParam.LOCATION).toString())
        }
    }

    /**
     * 관심사 선택 화면 전환
     */
    private fun toInterest() {
        val intent = Intent(this, InterestActivity::class.java)
        activityResult(intent) {
            viewModel.interest.setValue(getString(intent.getIntExtra(IntentParam.INTEREST, 0)))
        }
    }
}