package com.moondroid.project01_meetingapp.presentation.view.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.data.datasource.local.realm.DMRealm
import com.moondroid.project01_meetingapp.databinding.ActivitySignUpBinding
import com.moondroid.project01_meetingapp.domain.model.DMUser
import com.moondroid.project01_meetingapp.presentation.view.activity.InterestActivity
import com.moondroid.project01_meetingapp.presentation.view.activity.LocationActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import dagger.hilt.android.AndroidEntryPoint
import java.security.SecureRandom
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

    private lateinit var id: String                             // ID
    private lateinit var pw: String                             // PW - 유효성 확인 및 해시값 생성에만 사용
    private lateinit var name: String                           // 이름
    private lateinit var gender: String                         // 성별
    private lateinit var thumb: String                          // 썸네일 - 카카오 로그인이 아닌경우는 기본값으로 설정
    private var birth: String? = null                           // 생년월일
    private var location: String? = null                        // 관심지역
    private var interest: String? = null                        // 관심사
    private var fromKakao: Boolean = false                      // 카카오 로그인 여부
    private var year = 1990
    private var month = 0
    private var date = 1

    override fun init() {
        binding.activity = this
        binding.model = viewModel
        initView()
        initViewModel()
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
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayHomeAsUpEnabled(true)
            }

            binding.rgAccount.setOnCheckedChangeListener { group, checkedId ->
                try {
                    val rb = group.findViewById<RadioButton>(checkedId)
                    viewModel.gender.value = rb.text.toString()
                } catch (e: Exception) {
                    logException(e)
                }
            }

            //카카오 미등록 시 회원가입 처리
            fromKakao = intent.hasExtra(RequestParam.ID) &&
                    intent.hasExtra(RequestParam.NAME) &&
                    intent.hasExtra(RequestParam.THUMB)

            if (fromKakao) {
                binding.etId.gone(true)
                binding.etPw.gone(true)
                binding.etPw2.gone(true)
                binding.etName.gone(true)

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

            is Event.Toast -> {
                toast(getString(event.resId))
            }

            is Event.Message -> {
                showMessage(event.message)
            }

            is Event.Home -> {
                goToHomeActivity(ActivityTy.SIGN_IN)
            }
        }
    }

    /**
     * Observe ViewModel
     */
    private fun initViewModel() {
        viewModel.signUpResponse.observe(this) {
            try {
                log("requestSignUp() , Response => $it")

                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        toast(getString(R.string.alm_sign_up_success))
                        user = Gson().fromJson(it.body, DMUser::class.java)
                        DMRealm.getInstance().writeBlocking {
                            copyToRealm(user!!)
                        }
                        DMAnalyze.setProperty(user!!)
                        DMCrash.setProperty(user!!.id)
                        getMsgToken()
                    }

                    ResponseCode.ALREADY_EXIST -> {
                        toast(getString(R.string.error_id_already_exist))
                        binding.etId.requestFocus()
                    }

                    else -> {
                        showMessage(getString(R.string.error_sign_up_fail), "[E01 : ${it.code}]")
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

        viewModel.tokenResponse.observe(this@SignUpActivity) {
            try {
                DMAnalyze.logEvent("SignUp Success")
                goToHomeActivity(ActivityTy.SIGN_UP)
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    /**
     * 회원가입 정보 초기화
     */
    fun signUp(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            id = if (fromKakao) {
                intent.getStringExtra(RequestParam.ID).toString()
            } else {
                binding.etId.text.toString()
            }
            pw = if (fromKakao) {
                intent.getStringExtra(RequestParam.ID).toString()
            } else {
                binding.etPw.text.toString()
            }
            name = if (fromKakao) {
                intent.getStringExtra(RequestParam.NAME).toString()
            } else {
                binding.etName.text.toString()
            }
            birth = binding.tvBirth.text.toString()

            thumb = if (fromKakao) {
                intent.getStringExtra(RequestParam.THUMB).toString()
            } else {
                DEFAULT_PROFILE_IMG
            }

            checkField()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 회원가입 정보 유효성 체크
     */
    private fun checkField() {
        try {
            if (!fromKakao && !id.matches(Regex.ID)) {
                toast(getString(R.string.error_id_mismatch))
            } else if (!fromKakao && !pw.matches(Regex.PW)) {
                toast(getString(R.string.error_password_mismatch))
            } else if (!fromKakao && pw != binding.etPw2.text.toString()) {
                toast(getString(R.string.error_password_unchecked))
            } else if (!fromKakao && !name.matches(Regex.NAME)) {
                toast(getString(R.string.error_name_mismatch))
            } else if (birth.isNullOrEmpty()) {
                toast(getString(R.string.error_birth_empty))
            } else if (location.isNullOrEmpty()) {
                toast(getString(R.string.error_location_empty))
            } else if (interest.isNullOrEmpty()) {
                toast(getString(R.string.error_interest_empty))
            } else if (!binding.checkBox.isChecked) {
                toast(getString(R.string.alm_agree_to_use_terms_and_privacy_policy))
            } else {
                encryptPw()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 비밀번호 해시값 생성
     */
    private fun encryptPw() {
        try {
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
     */
    private fun requestSignUp(hashPw: String, salt: String) {
        try {
            val jsonObject = JsonObject()
            jsonObject.addProperty(RequestParam.ID, id)
            jsonObject.addProperty(RequestParam.HASH_PW, hashPw)
            jsonObject.addProperty(RequestParam.SALT, salt)
            jsonObject.addProperty(RequestParam.NAME, name)
            jsonObject.addProperty(RequestParam.BIRTH, birth)
            jsonObject.addProperty(RequestParam.GENDER, gender)
            jsonObject.addProperty(RequestParam.LOCATION, location)
            jsonObject.addProperty(RequestParam.INTEREST, interest)
            jsonObject.addProperty(RequestParam.THUMB, thumb)

            viewModel.signUp(jsonObject)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * FCM 토큰 생성
     * [토큰 생성되지 않은 경우에도 정상처리]
     */
    private fun getMsgToken() {
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    hideLoading()
                    goToHomeActivity(ActivityTy.SIGN_UP)
                    return@OnCompleteListener
                }

                val token = task.result

                log("getMsgToken() , token => $token")
                updateToken(token)
            })
        } catch (e: Exception) {
            logException(e)
        }
    }


    /**
     * 토큰 등록
     * [토큰 미등록시에도 정상처리]
     */
    private fun updateToken(token: String) {
        try {
            val body = JsonObject()

            body.addProperty(RequestParam.ID, id)
            body.addProperty(RequestParam.TOKEN, token)
            viewModel.updateToken(body)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * Salt 랜덤 변수 생성
     */
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
     */
    fun showDateDialog(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val datePicker = DatePickerDialog(
                this,
                R.style.DatePickerSpin,
                { _, p1, p2, p3 ->
                    year = p1
                    month = p2
                    date = p3
                    viewModel.birth.value = String.format(Locale.KOREA, "%d.%d.%d", year, month +1, date)
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
    fun goToLocationActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val onResult: (Intent) -> Unit = { intent ->
                viewModel.location.value = intent.getStringExtra(IntentParam.LOCATION).toString()
                //binding.tvLocation.text = location

                log("getLocation() - location : ${viewModel.location.value}")
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
    fun goToInterestActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            val onResult: (Intent) -> Unit = { intent ->
                viewModel.interest.value = getString(intent.getIntExtra(IntentParam.INTEREST, 0))
                //binding.tvInterest.text = interest

                log("getInterest() - Interest : ${viewModel.interest.value}")
            }
            val intent = Intent(this, InterestActivity::class.java)
            activityResult(onResult, intent)
        } catch (e: Exception) {
            logException(e)
        }
    }
}