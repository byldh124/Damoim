package com.moondroid.project01_meetingapp.presentation.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.moondroid.project01_meetingapp.BuildConfig
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySplashBinding
import com.moondroid.project01_meetingapp.utils.ActivityTy
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.logException
import com.moondroid.project01_meetingapp.utils.repeatOnStarted
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
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()

    override fun init() {
        DMAnalyze.logEvent("Splash_Loaded")
        initView()
        checkAppVersion()
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: Event) {
        when(event) {
            is Event.Message -> {
                showMessage(event.message)
            }
            is Event.Update -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=$packageName")
                startActivity(intent)
            }
            is Event.Main -> {
                goToHomeActivity()
            }
            is Event.Sign -> {
                goToSignInActivity()
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
                BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME
            )
        } catch (e: Exception) {
            logException(e)
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
            logException(e)
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