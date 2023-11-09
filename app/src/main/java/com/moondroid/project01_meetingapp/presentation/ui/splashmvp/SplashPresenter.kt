package com.moondroid.project01_meetingapp.presentation.ui.splashmvp

import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Preferences
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.app.CheckVersionUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.sign.SignInActivity
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashPresenter constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val view: SplashContract.View,
) : SplashContract.Presenter {
    override fun checkAppVersion(code: Int, name: String, pckName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            checkVersionUseCase(
                packageName = pckName,
                versionCode = code,
                versionName = name
            ).collect { result ->
                result.onSuccess {
                    debug("success")
                    checkSign()
                }.onFail {
                    if (it == ResponseCode.INVALID_VALUE) view.update()
                    else view.fail(it)
                }.onError {
                    view.error(it)
                }
            }
        }
    }

    private fun checkSign() {
        if (Preferences.isAutoSign()) {
            CoroutineScope(Dispatchers.Main).launch {
                profileUseCase().collect { result ->
                    result.onSuccess {
                        ProfileHelper.profile = it
                        view.screen(HomeActivity::class.java)
                    }.onError {
                        view.screen(SignInActivity::class.java)
                    }
                }
            }
        } else {
            view.screen(SignInActivity::class.java)
        }
    }
}

