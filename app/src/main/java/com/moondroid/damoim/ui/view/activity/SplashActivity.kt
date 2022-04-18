package com.moondroid.damoim.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.moondroid.damoim.R
import com.moondroid.damoim.application.DMApp
import com.moondroid.damoim.base.BaseActivity
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.ui.viewmodel.SplashViewModel
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.view.logException
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), Animation.AnimationListener {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_splash)
            initView()

            //checkAutoLogin()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun checkAutoLogin() {
        val userId = DMApp.prefs.getString(Constants.PrefKey.USER_ID)
        if (!userId.isNullOrEmpty()) {
            getUserInfo(userId)
        } else {

        }
    }

    fun getUserInfo(userId: String) {
        viewModel.getUserInfo(userId)

        viewModel.userInfo.observe(this) {
            DMApp.user = it
        }
    }


    fun initView() {
        container.startAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_anim))
        container.animation.setAnimationListener(this)
    }

    override fun onAnimationStart(animation: Animation?) {
        //DO NOTHING
    }

    override fun onAnimationEnd(animation: Animation?) {
        goToSgnnActivity(Constants.ActivityTy.SPLASH)
    }

    override fun onAnimationRepeat(animation: Animation?) {
        //DO NOTHING
    }

}