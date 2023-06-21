package com.moondroid.project01_meetingapp.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.moondroid.damoim.common.ActivityTy
import com.moondroid.damoim.common.Constants
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.repeatOnStarted
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivitySplashBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.splash.SplashViewModel.SplashEvent
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import dagger.hilt.android.AndroidEntryPoint

/**
 * 스플래시 액티비티
 *
 * checkAppVersion -> checkAutoLogin
 *   1) SignInActivity  (로그인 기록 x)
 *   2) HomeActivity    (로그인 기록 o)
 * */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DMAnalyze.logEvent("Splash_Loaded")
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
        initView()
        checkAppVersion()
    }


    private fun handleEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Message -> showMessage(event.message)
            is SplashEvent.Main -> goToHomeActivity()
            is SplashEvent.Sign -> goToSignInActivity()
            is SplashEvent.Update -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=$packageName")
                    startActivity(intent)
                } catch (e: Exception) {
                    e.logException()
                }
            }
        }
    }

    /**
     *  앱 버전 체크
     */
    private fun checkAppVersion() {
        try {
            viewModel.checkAppVersion(
                packageName,
                1,
                "0.0.1"
            )
        } catch (e: Exception) {
            e.logException()
        }
    }

    /**
     * Initialize View
     */
    private fun initView() {
        try {
            val animation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)
            binding.container.startAnimation(animation)
        } catch (e: Exception) {
            e.logException()
        }
    }

    private fun goToHomeActivity() {
        goToHomeActivity(ActivityTy.SPLASH)
    }

    private fun goToSignInActivity() {
        goToSignInActivity(ActivityTy.SPLASH)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.container.animation != null) {
            binding.container.clearAnimation()
        }
    }
}