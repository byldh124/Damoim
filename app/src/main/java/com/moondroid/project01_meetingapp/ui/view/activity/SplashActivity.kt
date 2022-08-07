package com.moondroid.project01_meetingapp.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.util.Util
import com.google.gson.Gson
import com.kakao.sdk.common.util.Utility
import com.moondroid.project01_meetingapp.BuildConfig
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySplashBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.view.dialog.ErrorDialog
import com.moondroid.project01_meetingapp.ui.viewmodel.SplashViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.view.exitApp
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executor

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding
    private lateinit var executor: Executor

    private var isReady = false
    private var animFinish = false
    lateinit var action: () -> Unit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
            binding.activity = this

            executor = ContextCompat.getMainExecutor(this)
            initView()

            var keyHash = Utility.getKeyHash(this)

            Log.e("KEY" , keyHash)

        } catch (e: Exception) {
            logException(e)
        }
    }

    /* Prefs 에 저장된 아이디 값 체크 */
    private fun checkAutoLogin() {
        try {
            val userInfo = DMApp.prefs.getString(Constants.PrefKey.USER_INFO)
            if (!userInfo.isNullOrEmpty()) {
                DMApp.user = Gson().fromJson(userInfo, User::class.java)

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

    private fun checkAppVersion() {
        viewModel.checkAppVersion(
            packageName,
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME
        )

        viewModel.appCheck.observe(this) {
            when (it) {
                Constants.ResponseCode.SUCCESS -> {
                    checkAutoLogin()
                }

                Constants.ResponseCode.INACTIVE -> {
                    executor.execute {
                        showError("새로운 버전으로 업데이트가 필요합니다.") {
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
                }

                else -> {
                    exitApp()
                }
            }
        }
    }


    private fun initView() {
        try {
            val animation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)

            animation.setAnimationListener(object : Animation.AnimationListener{
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

    private fun startAction(){
        if (isReady && animFinish){
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