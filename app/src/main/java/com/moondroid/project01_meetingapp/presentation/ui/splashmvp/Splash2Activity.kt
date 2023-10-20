package com.moondroid.project01_meetingapp.presentation.ui.splashmvp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.domain.usecase.app.CheckVersionUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.BuildConfig
import com.moondroid.project01_meetingapp.databinding.ActivitySplashBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.dialog.ButtonDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 스플래시 액티비티
 *
 * checkAppVersion -> checkAutoLogin
 *   1) SignInActivity  (로그인 기록 x)
 *   2) HomeActivity    (로그인 기록 o)
 * */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class Splash2Activity : BaseActivity(), SplashContract.View {
    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private lateinit var presenter: SplashContract.Presenter

    @Inject
    lateinit var checkVersionUseCase: CheckVersionUseCase
    @Inject
    lateinit var profileUseCase: ProfileUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SplashPresenter(checkVersionUseCase, profileUseCase, this)
        presenter.checkAppVersion(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, packageName)
    }

    override fun update() {
        val builder = ButtonDialog.Builder(mContext).apply {
            message = "새로운 버전이 출시됐습니다.\n업데이트 후 이용이 가능합니다."
            setPositiveButton("업데이트") {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=$packageName")
                    startActivity(intent)
                } catch (e: Exception) {
                    logException(e)
                }
            }
            setNegativeButton("나가기", ::finish)
        }
        builder.build()
    }

    override fun error(throwable: Throwable) {
        networkError(throwable)
    }

    override fun fail(code: Int) {
        serverError(code)
    }

    override fun <T : BaseActivity> screen(cls: Class<T>) {
        startActivity(Intent(mContext, cls))
        finish()
    }
}