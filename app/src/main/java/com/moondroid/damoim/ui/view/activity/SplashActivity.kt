package com.moondroid.damoim.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.databinding.ActivitySplashBinding
import com.moondroid.damoim.ui.viewmodel.SplashViewModel
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.view.logException
import org.koin.android.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
            binding.activity = this
            initView()
            checkAutoLogin()
        } catch (e: Exception) {
            logException(e)
        }
    }

    /* Prefs 에 저장된 아이디 값 체크 */
    private fun checkAutoLogin() {
        try {
            val userId = DMApp.prefs.getString(Constants.PrefKey.USER_ID)
            if (!userId.isNullOrEmpty()) {
                getUserInfo(userId)
            } else {
                goToSgnnActivity()

            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun getUserInfo(userId: String) {
        try {
            viewModel.getUserInfo(userId)
            viewModel.userInfo.observe(this, Observer {
                DMApp.user = it
                goToHomeActivity()
            })

            viewModel.userstatus.observe(this, Observer {
                if (it != 1000) {
                    goToSgnnActivity()
                }
            })
        } catch (e: Exception) {
            logException(e)
        }
    }


    private fun initView() {
        try {
            binding.container.startAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_anim))
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun goToHomeActivity() {
        goToHomeActivity(Constants.ActivityTy.SPLASH)
        finish()
    }

    private fun goToSgnnActivity() {
        goToSgnnActivity(Constants.ActivityTy.SPLASH)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.container.animation != null){
            binding.container.clearAnimation()
        }
    }
}