package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySignInBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.viewmodel.SignInViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {
    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: ActivitySignInBinding

    private lateinit var id: String
    private lateinit var pw: String

    private val idRegex = Regex("^[a-zA-Z0-9]{5,16}$")
    private val pwRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}\$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.activity = this
    }

    fun hideKeyBoard(@Suppress("UNUSED_PARAMETER") vw: View) {
        hideKeyboard()
    }

    fun checkField(@Suppress("UNUSED_PARAMETER") vw: View) {
        id = binding.etId.text.toString()
        pw = binding.etPw.text.toString()

        if (!id.matches(idRegex)) {
            toast("아이디는 5글자 이상 16글자 이하 영문, 숫자로 이루어져 있습니다.")
        } else if (!pw.matches(pwRegex)) {
            toast("비밀번호는 8글자 이상 영문, 숫자, 특수기호로 이루어져 있습니다.")
        } else {
            getSalt()
        }
    }

    private fun getSalt() {
        viewModel.getSalt(id)

        viewModel.saltResponse.observe(this) {

            log("[SignInActivity::getSalt] Response = $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val salt = it.body.asString
                    val hashPw = DMUtils.hashingPw(pw, salt)
                    signIn(hashPw)
                }

                Constants.ResponseCode.NOT_EXIST -> {
                    showError("해당 아이디가 존재하지 않습니다.") {
                        binding.etId.requestFocus()
                    }
                }
                else -> {
                    showError("로그인 실패 [E01]")
                }
            }
        }
    }

    private fun signIn(hashPw: String) {
        val body = JsonObject()
        body.addProperty("id", id)
        body.addProperty("hashPw", hashPw)

        viewModel.signIn(body)

        viewModel.signInResponse.observe(this) {

            log("[SignInActivity::signIn] Response = $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val userInfo = it.body
                    DMApp.user = Gson().fromJson(userInfo, User::class.java)
                    if (binding.checkBox.isChecked) {
                        DMApp.prefs.putString(Constants.PrefKey.USER_INFO, userInfo.toString())
                    }

                    goToHomeActivity()
                }

                Constants.ResponseCode.NOT_EXIST -> {
                    showError("해당 아이디가 존재하지 않습니다.") {
                        binding.etId.requestFocus()
                    }
                }

                Constants.ResponseCode.INVALID_VALUE -> {
                    showError("잘못된 비밀번호 입니다.") {
                        binding.etPw.requestFocus()
                    }
                }

                else -> {
                    showError("로그인 실패 [E02]")
                }
            }
        }
    }

    fun getKakaoProfile(vw: View) {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                logException(error)
                getKakaoProfile2(vw)
            }
            if (token != null) {
                UserApiClient.instance.me { user, error2 ->
                    if (error2 != null) {
                        logException(error2)
                    }
                    if (user != null) {
                        val id = user.id.toString()
                        val name = user.kakaoAccount?.profile?.nickname
                        val profileThumb = user.kakaoAccount?.profile?.profileImageUrl
                        if (name != null) {
                            if (profileThumb != null) {
                                signInWithKakao(vw, id, name, profileThumb)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getKakaoProfile2(vw: View) {
        DMLog.e("login with kakao account")
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            DMLog.e("login with kakao account2")
            if (error != null) {
                logException(error)
            }
            if (token != null) {
                DMLog.e("token is exist")
                UserApiClient.instance.me { user, error2 ->
                    if (error2 != null) {
                        logException(error2)
                    }
                    if (user != null) {
                        DMLog.e("user is exist")
                        val id = user.id.toString()
                        val name = user.kakaoAccount?.profile?.nickname
                        val profileThumb = user.kakaoAccount?.profile?.profileImageUrl
                        if (name != null) {
                            DMLog.e("name is exist")
                            if (profileThumb != null) {
                                DMLog.e("signInWithKakao")
                                signInWithKakao(vw, id, name, profileThumb)
                            } else {
                                DMLog.e("profileThumb is null")
                            }
                        } else {
                            DMLog.e("name is null")
                        }
                    } else {
                        DMLog.e("user is null")
                    }
                }
            } else {
                DMLog.e("token is null")
            }
        }
    }

    private fun signInWithKakao(vw: View, id: String, name: String, profileThumb: String) {
        val body = JsonObject()

        body.addProperty("id", id)

        viewModel.signInkakao(body)

        vw.isEnabled = false

        viewModel.kakaoSignInResponse.observe(this) {

            DMLog.e("[SignInActivity::signInKakao] Response : $it")

            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    val userInfo = it.body
                    DMApp.user = Gson().fromJson(userInfo, User::class.java)
                    if (binding.checkBox.isChecked) {
                        DMApp.prefs.putString(Constants.PrefKey.USER_INFO, userInfo.toString())
                    }
                    goToHomeActivity()
                }

                Constants.ResponseCode.NOT_EXIST -> {
                    val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("name", name)
                    intent.putExtra("thumb", profileThumb)

                    startActivityWithAnim(intent)
                }

                else -> {
                    showError("로그인 실패 [E03 : ${it.code}]")
                }
            }
        }
    }

    private fun goToHomeActivity() {
        goToHomeActivity(Constants.ActivityTy.SIGN_IN)
    }

    fun goToSignUp(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra(Constants.ACTIVITY_TY, Constants.ActivityTy.SIGN_IN)
        startActivityWithAnim(intent)
    }
}