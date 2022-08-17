package com.moondroid.project01_meetingapp.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.BuildConfig
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySplashBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.viewmodel.SplashViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.view.exitApp
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executor

/**
 * 스플래시 액티비티
 *
 * checkAppVersion -> checkAutoLogin
 *   1) SignInActivity  (로그인 기록 x)
 *   2) HomeActivity    (로그인 기록 o)
 * */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding
    private lateinit var executor: Executor

    private var isReady = false
    private var animFinish = false
    lateinit var action: () -> Unit         //애니메이션 이후 화면 전환


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
            binding.activity = this

            executor = ContextCompat.getMainExecutor(this)
            initView()

            initViewModel()

        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun initViewModel() {
        viewModel.appCheck.observe(this) {
            try {
                when (it) {
                    Constants.ResponseCode.SUCCESS -> {
                        checkAutoLogin()
                    }

                    Constants.ResponseCode.INACTIVE -> {
                        showError(getString(R.string.error_request_update)) {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse("market://details?id=$packageName")
                                startActivity(intent)
                            } catch (e: Exception) {
                                logException(e)
                                exitApp()
                            }
                        }
                    }

                    else -> {
                        showError(getString(R.string.error_version_not_checked)) {
                            exitApp()
                        }
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    /**
     * Prefs 에 저장된 아이디 값 체크
     **/
    private fun checkAutoLogin() {
        try {
            val userInfo = DMApp.prefs.getString(Constants.PrefKey.USER_INFO)
            if (!userInfo.isNullOrEmpty()) {
                DMApp.user = Gson().fromJson(userInfo, User::class.java)

                log("[SplashActivity] , checkAutoLogin , User => ${DMApp.user}")

                isReady = true
                action = { goToHomeActivity() }
                startAction()

            } else {
                isReady = true
                action = { goToSgnnActivity() }
                startAction()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     *  앱 버전 체크
     **/
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


    private fun initView() {
        try {
            val animation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)

            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    animFinish = true
                    startAction()
                }

                override fun onAnimationRepeat(p0: Animation?) {
                }

            })

            binding.container.startAnimation(animation)

            checkAppVersion()
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun startAction() {
        if (isReady && animFinish) {
            action()
        }
    }

    private fun goToHomeActivity() {
        goToHomeActivity(Constants.ActivityTy.SPLASH)
    }

    private fun goToSgnnActivity() {
        goToSgnnActivity(Constants.ActivityTy.SPLASH)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.container.animation != null) {
            binding.container.clearAnimation()
        }
    }
}