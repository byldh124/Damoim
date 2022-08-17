package com.moondroid.project01_meetingapp.ui.view.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySignUpBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.viewmodel.SignUpViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.view.gone
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.SecureRandom

/**
 * 회원 가입
 *
 * 1. 기입 정보 유효성 체크
 * 2. 해시 비밀번호 생성
 * 3. 회원가입
 **/
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    private lateinit var id: String                             // ID
    private lateinit var pw: String                             // PW - 유효성 확인 및 해시값 생성에만 사용
    private lateinit var name: String                           // 이름
    private lateinit var gender: String                         // 성별
    private lateinit var thumb: String                          // 썸네일 - 카카오 로그인이 아닌경우는 기본값으로 설정
    private var birth: String? = null                           // 생년월일
    private var location: String? = null                        // 관심지역
    private var interest: String? = null                        // 관심사
    private var fromKakao: Boolean = false                      // 카카오 로그인 여부

    private val idRegex =
        Regex("^[a-zA-Z0-9]{5,16}$")                                      // ID 정규식       [영문, 숫자 5-16글자]
    private val pwRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}\$")          // 비밀번호 정규식  [영문, 숫자, 특수기호 포함 8글자 이상]
    private val nameRegex =
        Regex("^(.{2,8})$")                                             // 이름 정규식     [2 - 8 글자]

    /* 관심지역 ActivityResult */
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

    /* 관심사 ActivityResult */
    private val getInterest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        interest = getString(it.getIntExtra(Constants.IntentParam.INTEREST, 0))
                        binding.tvInterest.text = interest
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
            binding.activity = this


            //카카오 미등록 시 회원가입 처리
            fromKakao =
                intent.hasExtra(Constants.RequestParam.ID) && intent.hasExtra(Constants.RequestParam.NAME) && intent.hasExtra(
                    Constants.RequestParam.THUMB
                )

            if (fromKakao) {
                binding.etId.gone(true)
                binding.etPw.gone(true)
                binding.etPw2.gone(true)
                binding.etName.gone(true)
            }

            gender = binding.rbAccountMale.text.toString()

            initView()

            initViewModel()

        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initView() {
        try {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayHomeAsUpEnabled(true)
            }

            binding.rgAccount.setOnCheckedChangeListener { group, checkedId ->
                try {
                    val rb = group.findViewById<RadioButton>(checkedId)
                    gender = rb.text.toString()
                } catch (e: Exception) {
                    logException(e)
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initViewModel() {

        viewModel.showLoading.observe(this) {
            try {
                if (it) {
                    showLoading()
                    binding.btnSave.isEnabled = false
                } else {
                    hideLoading()
                    binding.btnSave.isEnabled = true
                }
            } catch (e: Exception) {
                logException(e)
            }
        }


        viewModel.signUpResponse.observe(this) {

            DMLog.e("[SignUpActivity::requestSignUp] Response => $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    toast(getString(R.string.alm_sign_up_success))
                    DMApp.user = Gson().fromJson(it.body, User::class.java)
                    getMsgToken()
                }

                Constants.ResponseCode.ALREADY_EXIST -> {
                    toast(getString(R.string.error_id_already_exist))
                    binding.etId.requestFocus()
                }

                else -> {
                    showError(
                        String.format(
                            getString(R.string.error_sign_up_fail),
                            "[E01 : ${it.code}]"
                        )
                    )
                }
            }
        }

        viewModel.tokenResponse.observe(this@SignUpActivity) {
            try {
                log("[SignUpActivity::updateToken] Response: $it")
                hideLoading()
                goToHomeActivity(Constants.ActivityTy.SIGN_UP)
            } catch (e: Exception) {
                logException(e)
            }
        }


    }

    /**
     * 회원가입 정보 초기화
     **/
    fun signUp(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            id = if (fromKakao) {
                intent.getStringExtra(Constants.RequestParam.ID).toString()
            } else {
                binding.etId.text.toString()
            }
            pw = if (fromKakao) {
                intent.getStringExtra(Constants.RequestParam.ID).toString()
            } else {
                binding.etPw.text.toString()
            }
            name = if (fromKakao) {
                intent.getStringExtra(Constants.RequestParam.NAME).toString()
            } else {
                binding.etName.text.toString()
            }
            birth = binding.tvBirth.text.toString()

            thumb = if (fromKakao) {
                intent.getStringExtra(Constants.RequestParam.THUMB).toString()
            } else {
                Constants.DEFAULT_PROFILE_IMG
            }

            checkField()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 회원가입 정보 유효성 체크
     **/
    private fun checkField() {
        try {
            if (!fromKakao && !id.matches(idRegex)) {
                toast(getString(R.string.error_id_mismatch))
            } else if (!fromKakao && !pw.matches(pwRegex)) {
                toast(getString(R.string.error_password_mismatch))
            } else if (!fromKakao && pw != binding.etPw2.text.toString()) {
                toast(getString(R.string.error_password_unchecked))
            } else if (!fromKakao && !name.matches(nameRegex)) {
                toast(getString(R.string.error_name_mismatch))
            } else if (birth.isNullOrEmpty()) {
                toast(getString(R.string.error_birth_empty))
            } else if (location.isNullOrEmpty()) {
                toast(getString(R.string.error_location_empty))
            } else if (interest.isNullOrEmpty()) {
                toast(getString(R.string.error_interest_empty))
            } else {
                encryptPw()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 비밀번호 해시값 생성
     **/
    private fun encryptPw() {
        try {
            viewModel.showLoading.value = true

            val salt = getSalt()

            salt?.let {
                val hashPw = DMUtils.hashingPw(pw, it)
                pw = ""
                requestSignUp(hashPw, it)
            }

        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 회원가입
     **/
    private fun requestSignUp(hashPw: String, salt: String) {
        try {

            val jsonObject = JsonObject()
            jsonObject.addProperty(Constants.RequestParam.ID, id)
            jsonObject.addProperty(Constants.RequestParam.HASH_PW, hashPw)
            jsonObject.addProperty(Constants.RequestParam.SALT, salt)
            jsonObject.addProperty(Constants.RequestParam.NAME, name);
            jsonObject.addProperty(Constants.RequestParam.BIRTH, birth)
            jsonObject.addProperty(Constants.RequestParam.GENDER, gender)
            jsonObject.addProperty(Constants.RequestParam.LOCATION, location)
            jsonObject.addProperty(Constants.RequestParam.INTEREST, interest)
            jsonObject.addProperty(Constants.RequestParam.THUMB, thumb)

            viewModel.signUp(jsonObject)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * FCM 토큰 생성
     * [토큰 생성되지 않은 경우에도 정상처리]
     **/
    private fun getMsgToken() {
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    hideLoading()
                    goToHomeActivity(Constants.ActivityTy.SIGN_UP)
                    return@OnCompleteListener
                }

                val token = task.result

                DMLog.e("[SignUpActivity::getMsgToken] token => $token")
                updateToken(token)
            })
        } catch (e: Exception) {
            logException(e)
        }
    }


    /**
     * 토큰 등록
     * [토큰 미등록시에도 정상처리]
     **/
    private fun updateToken(token: String) {
        try {
            val body = JsonObject()

            body.addProperty(Constants.RequestParam.ID, id)
            body.addProperty(Constants.RequestParam.TOKEN, token)
            viewModel.updateToken(body)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * Salt 랜덤 변수 생성
     **/
    private fun getSalt(): String? {
        return try {
            val rnd = SecureRandom()
            val temp = ByteArray(16)
            rnd.nextBytes(temp)

            DMUtils.byteToString(temp)
        } catch (e: Exception) {
            logException(e)
            null
        }
    }

    /**
     * Android 달력 다이얼로그 호출
     **/
    fun showDateDialog(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val datePicker = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    binding.tvBirth.text = String.format("%d.%d.%d", p1, p2 + 1, p3)
                }
            }, 1990, 0, 1)

            datePicker.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 관심지역 선택 화면 전환
     **/
    fun goToLocationActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        getLocation.launch(Intent(this, LocationActivity::class.java))
    }

    /**
     * 관심사 선택 화면 전환
     **/
    fun goToInterestActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        getInterest.launch(Intent(this, InterestActivity::class.java))
    }
}