package com.moondroid.damoim.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApplication
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.ui.viewmodel.HomeViewModel
import com.moondroid.damoim.ui.viewmodel.SignInViewModel
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.view.hideKeyboard
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
            viewModel.getUserInfo(userId)

            viewModel.userInfo.observe(this) {
                DMApplication.user = it
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


}