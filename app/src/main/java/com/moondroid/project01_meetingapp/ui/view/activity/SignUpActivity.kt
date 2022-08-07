package com.moondroid.project01_meetingapp.ui.view.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    private lateinit var id: String
    private lateinit var pw: String
    private lateinit var name: String
    private lateinit var gender: String
    private lateinit var thumb: String
    private var birth: String? = null
    private var location: String? = null
    private var interest: String? = null
    private var doSignUp = false
    private var fromKakao : Boolean = false

    private val idRegex = Regex("^[a-zA-Z0-9]{5,16}$")
    private val pwRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}\$")
    private val nameRegex = Regex("^(.{2,8})$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
            binding.activity = this

            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayHomeAsUpEnabled(true)
            }

            fromKakao = intent.hasExtra("id") && intent.hasExtra("name") && intent.hasExtra("thumb")

            if (fromKakao){
                binding.etId.gone(true)
                binding.etPw.gone(true)
                binding.etPw2.gone(true)
                binding.etName.gone(true)
            }

            gender = binding.rbAccountMale.text.toString()

            binding.rgAccount.setOnCheckedChangeListener { group, checkedId ->
                val rb = group.findViewById<RadioButton>(checkedId)
                gender = rb.text.toString()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    fun signUp(@Suppress("UNUSED_PARAMETER") vw: View) {
        if (doSignUp) return
        doSignUp = true
        showLoading()
        id = if (fromKakao){
            intent.getStringExtra("id").toString()
        } else {
            binding.etId.text.toString()
        }
        pw = if (fromKakao) {
            intent.getStringExtra("id").toString()
        } else {
            binding.etPw.text.toString()
        }
        name = if (fromKakao) {
            intent.getStringExtra("name").toString()
        } else {
            binding.etName.text.toString()
        }
        birth = binding.tvBirth.text.toString()

        thumb = if (fromKakao){
            intent.getStringExtra("thumb").toString()
        } else {
            Constants.DEFAULT_PROFILE_IMG
        }

        checkField()
    }

    private fun reset() {
        hideLoading()
        doSignUp = false
    }

    private fun checkField() {
        if (!fromKakao && !id.matches(idRegex)) {
            toast("아이디는 5글자 이상 16글자 이하 영문, 숫자 사용이 가능합니다.")
            reset()
        } else if (!fromKakao && !pw.matches(pwRegex)) {
            toast("비밀번호는 8글자 이상 영문, 숫자, 특수기호가 포함되어야 합니다.")
            reset()
        } else if (!fromKakao && pw != binding.etPw2.text.toString()) {
            toast("입력하실 비밀번호가 일치하지 않습니다.")
            reset()
        } else if (!fromKakao && !name.matches(nameRegex)) {
            toast("이름은 2글자 이상 8글자 이하만 가능합니다.")
            reset()
        } else if (birth.isNullOrEmpty()) {
            toast("생년월일을 선택해주세요.")
            reset()
        } else if (location.isNullOrEmpty()) {
            toast("관심지역을 선택해주세요.")
            reset()
        } else if (interest.isNullOrEmpty()) {
            toast("관심사를 선택해주세요.")
            reset()
        } else {
            encryptPw()
        }
    }

    private fun encryptPw() {
        val salt = getSalt()
        val hashPw = DMUtils.hashingPw(pw, salt)

        requestSignUp(hashPw, salt)
    }

    private fun requestSignUp(hashPw: String, salt: String) {

        val jsonObject = JsonObject()
        jsonObject.addProperty("id", id)
        jsonObject.addProperty("hashPw", hashPw)
        jsonObject.addProperty("salt", salt)
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("birth", birth)
        jsonObject.addProperty("gender", gender)
        jsonObject.addProperty("location", location)
        jsonObject.addProperty("interest", interest)
        jsonObject.addProperty("thumb", thumb)

        DMLog.e("[SignUpActivity::requestSignUp] Request body => $jsonObject")

        viewModel.signUp(jsonObject)

        viewModel.signUpResponse.observe(this) {

            DMLog.e("[SignUpActivity::requestSignUp] Response => $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    toast("회원가입 성공")
                    DMApp.user = Gson().fromJson(it.body, User::class.java)
                    getMsgToken()
                }

                Constants.ResponseCode.ALREADY_EXIST -> {
                    toast("이미 존재하는 아이디입니다.")
                    binding.etId.requestFocus()
                    reset()
                }

                else -> {
                    showError("회원가입 실패")
                    reset()
                }
            }
        }
    }

    private fun getMsgToken() {
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
    }

    private fun updateToken(token: String) {
        val body = JsonObject()

        body.addProperty("id", id)
        body.addProperty("token", token)
        viewModel.updateToken(body)

        viewModel.tokenResponse.observe(this@SignUpActivity) {
            log("[SignUpActivity::updateToken] Response: $it")
            hideLoading()
            goToHomeActivity(Constants.ActivityTy.SIGN_UP)
        }
    }

    private fun getSalt(): String {
        val rnd = SecureRandom()
        val temp = ByteArray(16)
        rnd.nextBytes(temp)

        return DMUtils.byteToString(temp)
    }

    fun goToLocationActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        getLocation.launch(Intent(this, LocationActivity::class.java))
    }

    private val getLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    location = it.getStringExtra(Constants.IntentParam.LOCATION).toString()
                    binding.tvLocation.text = location
                }
            }
        }

    fun showDateDialog(@Suppress("UNUSED_PARAMETER") vw: View) {
        val datePicker = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                binding.tvBirth.text = String.format("%d.%d.%d", p1, p2 + 1, p3)
            }
        }, 1990, 0, 1)

        datePicker.show()
    }

    fun goToInterestActivity(@Suppress("UNUSED_PARAMETER") vw: View) {
        getInterest.launch(Intent(this, InterestActivity::class.java))
    }

    private val getInterest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    interest = it.getStringExtra(Constants.IntentParam.INTEREST).toString()
                    binding.tvInterest.text = interest
                }
            }
        }
}