package com.moondroid.damoim.ui.view.activity

import android.os.Bundle
import android.view.View
import com.kakao.sdk.user.UserApiClient
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.ui.viewmodel.SignInViewModel
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.view.hideKeyboard
import com.moondroid.damoim.utils.view.logException
import com.moondroid.damoim.utils.view.toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {
    private val viewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun hideKeyBoard(vw: View) {
        hideKeyboard()
    }

    fun signIn(vw: View) {
        val userId = userId.text.toString()
        if (userId.isEmpty()) {
            toast("아이디를 입력해주세요.")
        } else {
            vw.isEnabled = false
            viewModel.signIn(userId)

            viewModel.userInfo.observe(this) {
                DMApp.user = it
                DMLog.e(it.toString())
                goToHomActivity(Constants.ActivityTy.SIGN_IN)
            }

            viewModel.userstatus.observe(this) {
                if (it == 2001) {

                }
                vw.isEnabled = true
            }
        }
    }

    fun getKakaoProfile(vw: View) {
        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
            if (error != null) {
                logException(error)
            }
            if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        logException(error)
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

    fun signInWithKakao(vw: View, id: String, name: String, profileThumb: String) {
        viewModel.signInWidthKakao(id, name, profileThumb)

        vw.isEnabled = false

        viewModel.userInfoKakao.observe(this) {
            DMApp.user = it
            DMLog.e(it.toString())
            goToHomActivity(Constants.ActivityTy.SIGN_IN)
        }

        viewModel.userstatusKakao.observe(this) {
            if (it == 2001) {

            }
            vw.isEnabled = true
        }
    }


}