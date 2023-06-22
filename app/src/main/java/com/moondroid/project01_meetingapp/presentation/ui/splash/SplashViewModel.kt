package com.moondroid.project01_meetingapp.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Preferences
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.app.VersionUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionUseCase: VersionUseCase,
    private val profileUseCase: ProfileUseCase
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<SplashEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            versionUseCase(packageName, versionCode, versionName).collect { result ->
                result.onSuccess {
                    checkUser()
                }.onFail {
                    event(SplashEvent.Version(it))
                }.onError {
                    message(it.message.toString())
                }
            }
        }
    }

    private fun checkUser() {
        if (Preferences.isAutoSign()) {
            viewModelScope.launch(Dispatchers.IO) {
                profileUseCase().collect { result ->
                    result.onSuccess {
                        main()
                    }.onError {
                        sign()
                    }
                }
            }
        } else {
            sign()
        }
    }

    private fun message(msg: String) = event(SplashEvent.Message(msg))
    private fun sign() = event(SplashEvent.Sign)
    private fun main() = event(SplashEvent.Main)

    private fun event(splashEvent: SplashEvent) {
        viewModelScope.launch {
            _eventFlow.emit(splashEvent)
        }
    }

    sealed class SplashEvent {
        data class Message(val message: String) : SplashEvent()
        data class Version(val responseCode: Int) : SplashEvent()
        object Sign : SplashEvent()
        object Main : SplashEvent()
    }
}

