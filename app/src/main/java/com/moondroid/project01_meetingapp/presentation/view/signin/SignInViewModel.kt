package com.moondroid.project01_meetingapp.presentation.view.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.usecase.sign.GetSaltUseCase
import com.moondroid.project01_meetingapp.domain.usecase.sign.SignInSocialUseCase
import com.moondroid.project01_meetingapp.domain.usecase.sign.SignInUseCase
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getSaltUseCase: GetSaltUseCase,
    private val signInSocialUseCase: SignInSocialUseCase
) : BaseViewModel() {

    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val isChecked = MutableLiveData(true)

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    /**
     * 입력값 유효성 확인
     */
    fun checkField() {
        try {
            if (!id.value.isNullOrEmpty() && !id.value.toString().matches(Regex.ID)) {
                toast(R.string.error_id_mismatch)
            } else if (!pw.value.isNullOrEmpty() && !pw.value.toString().matches(Regex.PW)) {
                toast(R.string.error_password_mismatch)
            } else {
                getSalt(id.value.toString())
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun getSalt(id: String) {
        loading(true)
        viewModelScope.launch {
            getSaltUseCase(id).collect { result ->
                loading(false)
                result.onSuccess {
                    when (it.code) {
                        ResponseCode.SUCCESS -> {
                            val salt = it.body.asString
                            val hashPw = DMUtils.hashingPw(pw.value.toString(), salt)
                            val body = JsonObject()
                            body.addProperty(RequestParam.ID, id)
                            body.addProperty(RequestParam.HASH_PW, hashPw)
                            signIn(body)
                        }
                        ResponseCode.NOT_EXIST -> {
                            toast(R.string.error_id_not_exist)
                        }

                        ResponseCode.FAIL -> {
                            message(it.msg)
                        }
                    }
                }.onError { message(it.message.toString()) }
            }
        }
    }

    fun signIn(body: JsonObject) {
        loading(true)
        viewModelScope.launch {
            signInUseCase(body).collect { result ->
                loading(false)
                result.onSuccess {
                    log("signIn() - result.onSuccess")
                    isChecked.value?.let { it1 -> DMApp.prefs.putBoolean(PrefsKey.AUTO_LOGIN, it1) }
                    home()
                }.onError {
                    message(it.message.toString())
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
        val body = JsonObject()

        body.addProperty(RequestParam.ID, id)

        viewModelScope.launch(Dispatchers.IO) {
            signInSocialUseCase(body).collect { result ->
                result.onSuccess {
                    when (it.code) {
                        ResponseCode.SUCCESS -> {
                            DMAnalyze.logEvent("SignIn_Success[Social]")
                            home()
                        }

                        ResponseCode.NOT_EXIST -> {
                            signUpSocial(id, name, thumb)
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

    private fun loading(b: Boolean) = event(Event.Loading(b))
    fun message(msg: String) = event(Event.Message(msg))
    fun toast(resId: Int) = event(Event.Toast(resId))
    private fun home() = event(Event.Home)
    fun signUp() = event(Event.SignUp)
    private fun signUpSocial(id: String, name: String, thumb: String) =
        event(Event.SignUpSocial(id = id, name = name, thumb = thumb))

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}

sealed class Event {
    data class Loading(val show: Boolean) : Event()
    data class Message(val message: String) : Event()
    data class Toast(val resId: Int) : Event()
    object Home : Event()
    object SignUp : Event()
    data class SignUpSocial(val id: String, val name: String, val thumb: String) : Event()
}