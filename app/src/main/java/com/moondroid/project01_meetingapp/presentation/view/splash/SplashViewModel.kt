package com.moondroid.project01_meetingapp.presentation.view.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.domain.usecase.app.CheckAppVersionUseCase
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.usecase.app.GetUserUseCase
import com.moondroid.project01_meetingapp.utils.PrefsKey
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAppVersionUseCase: CheckAppVersionUseCase,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            checkAppVersionUseCase(packageName, versionCode, versionName).collect { result ->
                result.onSuccess {
                    log("checkAppVersion() - Response: $it")
                    when (it.code) {
                        ResponseCode.SUCCESS -> {
                            checkUser()
                        }

                        ResponseCode.INACTIVE -> {
                            update()
                        }

                        ResponseCode.FAIL -> {
                            message(it.msg)
                        }
                    }
                }.onError {
                    message(it.message.toString())
                }
            }
        }
    }

    private fun checkUser() {
        log("checkUser() api call")
        val autoLogin = DMApp.prefs.getBoolean(PrefsKey.AUTO_LOGIN)
        log("checkUser() - autoLogin : $autoLogin")

        if (autoLogin) {
            viewModelScope.launch(Dispatchers.IO) {
                getUserUseCase.execute().collect { result ->
                    result.onSuccess {
                        log("checkUser() - User: $it")
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

    fun message(msg: String) = event(Event.Message(msg))
    fun update() = event(Event.Update)
    fun sign() = event(Event.Sign)
    fun main() = event(Event.Main)

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}

sealed class Event {
    data class Message(val message: String) : Event()
    object Update : Event()
    object Sign : Event()
    object Main : Event()
}