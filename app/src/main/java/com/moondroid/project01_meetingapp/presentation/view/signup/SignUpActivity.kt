package com.moondroid.project01_meetingapp.presentation.view.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.RadioButton
import androidx.activity.viewModels
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySignUpBinding
import com.moondroid.project01_meetingapp.presentation.view.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.view.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
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
class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

    private var year = 1990
    private var month = 0
    private var date = 1

    override fun init() {
        binding.model = viewModel
        initView()
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Initialize View
     */
    private fun initView() {
        try {
            binding.toolbar.init(this)

            binding.rgAccount.setOnCheckedChangeListener { group, checkedId ->
                try {
                    val rb = group.findViewById<RadioButton>(checkedId)
                    viewModel.gender.value = rb.text.toString()
                } catch (e: Exception) {
                    logException(e)
                }
            }

            //카카오 미등록 시 회원가입 처리
            val fromKakao = intent.hasExtra(RequestParam.ID) &&
                    intent.hasExtra(RequestParam.NAME) &&
                    intent.hasExtra(RequestParam.THUMB)

            viewModel.fromKakao = fromKakao

            if (fromKakao) {
                viewModel.id.value = intent.getStringExtra(RequestParam.ID)
                viewModel.pw.value = intent.getStringExtra(RequestParam.ID)
                viewModel.pw2.value = intent.getStringExtra(RequestParam.ID)
                viewModel.name.value = intent.getStringExtra(RequestParam.NAME)
                viewModel.thumb.value = intent.getStringExtra(RequestParam.THUMB)
            }

            viewModel.gender.value = binding.rbAccountMale.text.toString()

        } catch (e: Exception) {
            logException(e)
        }
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
            Event.Date -> showDateDialog()
            Event.Interest -> toInterest()
            Event.Location -> toLocation()
            Event.Privacy -> showPrivacy()
            Event.UseTerm -> showUseTerm()
        }
    }

    /**
     * Android 달력 다이얼로그 호출
     */
    fun showDateDialog() {
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
            logException(e)
        }
    }

    /**
     * 관심지역 선택 화면 전환
     */
    fun toLocation() {
        try {
            val onResult: (Intent) -> Unit = { intent ->
                viewModel.location.value = intent.getStringExtra(IntentParam.LOCATION).toString()
            }
            val intent = Intent(this, LocationActivity::class.java)
            activityResult(onResult, intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 관심사 선택 화면 전환
     */
    fun toInterest() {
        try {
            val onResult: (Intent) -> Unit = { intent ->
                viewModel.interest.value = getString(intent.getIntExtra(IntentParam.INTEREST, 0))
            }
            val intent = Intent(this, InterestActivity::class.java)
            activityResult(onResult, intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}