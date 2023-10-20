package com.moondroid.project01_meetingapp.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Preferences
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.app.VersionUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.DMApp
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

    private val _eventFlow = MutableEventFlow<Event>()
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
                }.onFail { code ->
                    when (code) {
                        ResponseCode.INVALID_VALUE -> event(Event.Update)
                        else -> event(Event.Fail(code))
                    }
                }.onError { t ->
                    event(Event.NetworkError(t))
                }
            }
        }
    }

    private fun checkUser() {
        if (Preferences.isAutoSign()) {
            viewModelScope.launch(Dispatchers.IO) {
                profileUseCase().collect { result ->
                    result.onSuccess {
                        DMApp.profile = it
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

    private fun sign() = event(Event.Sign)
    private fun main() = event(Event.Main)

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed interface Event {
        data object Update : Event
        data object Sign : Event
        data object Main : Event
        data class Fail(val code: Int) : Event
        data class NetworkError(val throwable: Throwable) : Event
    }
}

