package com.moondroid.project01_meetingapp.presentation.ui.signin

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.toast
import com.moondroid.damoim.common.PrefsKey
import com.moondroid.damoim.common.Regex
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.domain.model.status.ApiException
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.damoim.domain.usecase.sign.SaltUseCase
import com.moondroid.damoim.domain.usecase.sign.SignInSocialUseCase
import com.moondroid.damoim.domain.usecase.sign.SignInUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.project01_meetingapp.utils.*
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
    private val profileUseCase: ProfileUseCase,
    private val signInSocialUseCase: SignInSocialUseCase
) : BaseViewModel() {

    val id = MutableLiveData<String?>()
    val pw = MutableLiveData<String?>()
    val isChecked = MutableLiveData(true)

    private val _eventFlow = MutableEventFlow<SignInEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    /**
     * 입력값 유효성 확인
     */
    fun checkField() {
        try {
            if (!id.value.isNullOrEmpty() && !id.value.toString().matches(Regex.ID)) {
                context.toast(R.string.error_id_mismatch)
            } else if (!pw.value.isNullOrEmpty() && !pw.value.toString().matches(Regex.PW)) {
                context.toast(R.string.error_password_mismatch)
            } else {
                getSalt(id.value.toString())
            }
        } catch (e: Exception) {
            e.logException()
        }
    }

    private fun getSalt(id: String) {
        loading(true)
        viewModelScope.launch {
            saltUseCase(id).collect { result ->
                loading(false)
                result.onSuccess {
                    val hashPw = DMUtils.hashingPw(pw.value.toString(), it)
                    val body = JsonObject()
                    body.addProperty(RequestParam.ID, id)
                    body.addProperty(RequestParam.HASH_PW, hashPw)
                    signIn(body)

                }.onError {
                    message(it.message.toString())
                }
            }
        }
    }

    private fun signIn(body: JsonObject) {
        loading(true)
        viewModelScope.launch {
            signInUseCase(body).collect { result ->
                result.onSuccess {
                    isChecked.value?.let { it1 -> DMApp.prefs.putBoolean(PrefsKey.AUTO_LOGIN, it1) }
                    home()
                }.onError {
                    message(it.message.toString())
                }
                loading(false)
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
                    isChecked.value?.let { it1 -> DMApp.prefs.putBoolean(PrefsKey.AUTO_LOGIN, it1) }
                    home()
                }.onError {
                    if (it is ApiException && it.code == ResponseCode.NOT_EXIST) {
                        signUpSocial(id, name, thumb)
                    }
                    message(it.message.toString())
                }
            }
        }
    }

    private fun loading(b: Boolean) = event(SignInEvent.Loading(b))
    fun message(msg: String) = event(SignInEvent.Message(msg))
    private fun home() = event(SignInEvent.Home)
    fun signUp() = event(SignInEvent.SignUp)
    private fun signUpSocial(id: String, name: String, thumb: String) =
        event(SignInEvent.SignUpSocial(id = id, name = name, thumb = thumb))

    private fun event(signInEvent: SignInEvent) {
        viewModelScope.launch {
            _eventFlow.emit(signInEvent)
        }
    }

    sealed class SignInEvent {
        data class Loading(val show: Boolean) : SignInEvent()
        data class Message(val message: String) : SignInEvent()
        object Home : SignInEvent()
        object SignUp : SignInEvent()
        data class SignUpSocial(val id: String, val name: String, val thumb: String) : SignInEvent()
    }
}

