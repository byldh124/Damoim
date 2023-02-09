package com.moondroid.project01_meetingapp.presentation.view.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.domain.usecase.app.VersionUseCase
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.usecase.user.UserUseCase
import com.moondroid.project01_meetingapp.utils.PrefsKey
import com.moondroid.project01_meetingapp.utils.ResponseCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val versionUseCase: VersionUseCase,
    private val userUseCase: UserUseCase
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
        val autoLogin = DMApp.prefs.getBoolean(PrefsKey.AUTO_LOGIN)
        if (autoLogin) {
            viewModelScope.launch(Dispatchers.IO) {
                userUseCase().collect { result ->
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

    private fun message(msg: String) = event(Event.Message(msg))
    private fun update() = event(Event.Update)
    private fun sign() = event(Event.Sign)
    private fun main() = event(Event.Main)

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