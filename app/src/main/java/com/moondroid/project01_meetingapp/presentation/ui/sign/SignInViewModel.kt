package com.moondroid.project01_meetingapp.presentation.ui.sign

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.Extension.hashingPw
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Preferences
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.sign.SaltUseCase
import com.moondroid.damoim.domain.usecase.sign.SignInUseCase
import com.moondroid.damoim.domain.usecase.sign.SocialSignUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signInUseCase: SignInUseCase,
    private val saltUseCase: SaltUseCase,
    private val socialSignUseCase: SocialSignUseCase
) : BaseViewModel() {

    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val autoSign = MutableLiveData(true)

    private val _eventFlow = MutableEventFlow<SignInEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    /**
     * 입력값 유효성 확인
     */
    fun checkField() {
        try {
            if (!id.value.isNullOrEmpty() && !id.value.toString().matches(DMRegex.ID)) {
                context.toast(R.string.error_id_mismatch)
            } else if (!pw.value.isNullOrEmpty() && !pw.value.toString().matches(DMRegex.PW)) {
                context.toast(R.string.error_password_mismatch)
            } else {
                getSalt(id.value.toString())
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun getSalt(id: String) {
        viewModelScope.launch {
            saltUseCase(id).collect { result ->
                result.onSuccess {
                    val hashPw = hashingPw(pw.value.toString(), it)
                    signIn(id, hashPw)
                }.onFail {
                    event(SignInEvent.NotExist)
                }.onError {
                    event(SignInEvent.Error(it))
                }
            }
        }
    }

    private fun signIn(id: String, hashPw: String) {
        loading(true)
        viewModelScope.launch {
            signInUseCase.signIn(id, hashPw).collect { result ->
                loading(false)
                result.onSuccess {
                    Preferences.setAutoSign(autoSign.value == true)
                    DMApp.profile = it
                    home()
                }.onFail {
                    when (it) {
                        ResponseCode.INVALID_VALUE -> event(SignInEvent.InvalidPw)
                        ResponseCode.NOT_EXIST -> event(SignInEvent.NotExist)
                        else -> event(SignInEvent.Fail(it))
                    }
                }.onError {
                    event(SignInEvent.Error(it))
                }
            }
        }
    }

    /**
     * 카카오 로그인
     * [a] 기존 데이터 o : 회원 정보 요청
     * [b] 기존 데이터 x : 회원가입 화면 전환
     */
    fun signInSocial(id: String, name: String, thumb: String) {
        viewModelScope.launch(Dispatchers.IO) {
            socialSignUseCase(id).collect { result ->
                result.onSuccess {
                    Preferences.setAutoSign(autoSign.value == true)
                    home()
                }.onFail {
                    if (it == ResponseCode.NOT_EXIST) signUpSocial(id, name, thumb)
                }
            }
        }
    }

    private fun loading(b: Boolean) = event(SignInEvent.Loading(b))
    private fun message(msg: String) = event(SignInEvent.Message(msg))
    private fun home() = event(SignInEvent.Home)
    private fun signUpSocial(id: String, name: String, thumb: String) =
        event(SignInEvent.SignUpSocial(id = id, name = name, thumb = thumb))

    private fun event(signInEvent: SignInEvent) {
        viewModelScope.launch {
            _eventFlow.emit(signInEvent)
        }
    }

    sealed interface SignInEvent {
        data class Loading(val show: Boolean) : SignInEvent
        data class Message(val message: String) : SignInEvent
        data class Fail(val code: Int) : SignInEvent
        data class Error(val throwable: Throwable) : SignInEvent
        data class SignUpSocial(val id: String, val name: String, val thumb: String) : SignInEvent

        data object InvalidPw : SignInEvent
        data object NotExist : SignInEvent
        data object Home : SignInEvent
    }
}

