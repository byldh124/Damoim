package com.moondroid.project01_meetingapp.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.BuildConfig
import com.moondroid.project01_meetingapp.databinding.ActivitySplashBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.base.viewModel
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.dialog.ButtonDialog
import com.moondroid.project01_meetingapp.presentation.ui.splash.SplashViewModel.*
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
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
class SplashActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectEvent(viewModel.eventFlow, ::handleEvent)
        viewModel.checkAppVersion(packageName, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Main -> goToHomeActivity()
            is Event.Sign -> goToSignInActivity()
            Event.Update -> toUpdate()
        }
    }

    /**
     * 앱 버전이 비활성화 된 버전일 경우 업데이트 유도
     */
    private fun toUpdate() {
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
        builder.show()
    }
}