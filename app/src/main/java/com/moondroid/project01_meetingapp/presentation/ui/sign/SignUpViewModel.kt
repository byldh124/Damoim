package com.moondroid.project01_meetingapp.presentation.ui.sign

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.Extension.byteToString
import com.moondroid.damoim.common.Extension.hashingPw
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.common.crashlytics.FBCrash
import com.moondroid.damoim.domain.model.status.onError

import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.damoim.domain.usecase.sign.SignUpUseCase
import com.moondroid.damoim.domain.usecase.profile.UpdateTokenUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import com.moondroid.project01_meetingapp.utils.firebase.FBAnalyze
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signUpUseCase: SignUpUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase
) : BaseViewModel() {

    val id = MutableLiveData<String>()                             // ID
    val pw = MutableLiveData<String>()                             // PW - 유효성 확인 및 해시값 생성에만 사용
    val pw2 = MutableLiveData<String>()                            // PW - 유효성 확인 및 해시값 생성에만 사용
    val name = MutableLiveData<String>()                           // 이름
    val gender = MutableLiveData<String>()                         // 성별

    // 썸네일 - 카카오 로그인이 아닌경우는 기본값으로 설정
    val thumb = MutableLiveData("http://moondroid.dothome.co.kr/damoim/thumbs/IMG_20210302153242unnamed.jpg")
    val birth = MutableLiveData<String>()                          // 생년월일
    val location = MutableLiveData<String>()                       // 관심지역
    val interest = MutableLiveData<String>()                       // 관심사
    val toAgree = MutableLiveData(false)

    var fromSocial = false

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun signUp() {
        try {
            if (!fromSocial && !id.value.toString().matches(DMRegex.ID)) {
                context.toast(R.string.error_id_mismatch)
            } else if (!fromSocial && !pw.value.toString().matches(DMRegex.PW)) {
                context.toast(R.string.error_password_mismatch)
            } else if (!fromSocial && pw.value != pw2.value) {
                context.toast(R.string.error_password_unchecked)
            } else if (!fromSocial && !name.value.toString().matches(DMRegex.NAME)
            ) {
                context.toast(R.string.error_name_mismatch)
            } else if (birth.value.isNullOrEmpty()) {
                context.toast(R.string.error_birth_empty)
            } else if (location.value.isNullOrEmpty()) {
                context.toast(R.string.error_location_empty)
            } else if (interest.value.isNullOrEmpty()) {
                context.toast(R.string.error_interest_empty)
            } else if (toAgree.value != true) {
                context.toast(R.string.alm_agree_to_use_terms_and_privacy_policy)
            } else {
                encryptPw()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 비밀번호 해시값 생성
     */
    private fun encryptPw() {
        try {
            val salt = getSalt()
            salt?.let {
                val hashPw = hashingPw(pw.value!!, it)
                signUp(
                    id.value!!,
                    hashPw,
                    salt,
                    name.value!!,
                    birth.value!!,
                    gender.value!!,
                    location.value!!,
                    interest.value!!,
                    thumb.value!!
                )
            }

        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * Salt 랜덤 변수 생성
     */
    private fun getSalt(): String? {
        return try {
            val rnd = SecureRandom()
            val temp = ByteArray(16)
            rnd.nextBytes(temp)

            byteToString(temp)
        } catch (e: Exception) {
            logException(e)
            null
        }
    }

    private fun signUp(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String
    ) {
        loading(true)
        viewModelScope.launch {
            signUpUseCase(id, hashPw, salt, name, birth, gender, location, interest, thumb).collect { result ->
                loading(false)
                result.onSuccess {
                    FBAnalyze.setProperty(it)
                    FBCrash.setProperty(it.id)
                    ProfileHelper.profile = it
                    getMsgToken()
                }.onFail {
                    if (it == ResponseCode.ALREADY_EXIST) context.toast(R.string.error_id_already_exist)
                }.onError { message(it.message.toString()) }
            }
        }
    }

    /**
     * FCM 토큰 생성
     * [토큰 생성되지 않은 경우에도 정상처리]
     */
    private fun getMsgToken() {
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    loading(false)
                    home()
                    return@OnCompleteListener
                }

                val token = task.result
                updateToken(token)
            })
        } catch (e: Exception) {
            logException(e)
        }
    }


    /**
     * 토큰 등록
     * [토큰 미등록시에도 정상처리]
     */
    private fun updateToken(token: String) {
        viewModelScope.launch {
            updateTokenUseCase(ProfileHelper.profile.id, token).collect {
                loading(false)
                home()
            }
        }
    }

    private fun loading(b: Boolean) = event(Event.Loading(b))
    fun message(msg: String) = event(Event.Message(msg))
    private fun home() = event(Event.Home)

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed interface Event {
        data class Fail(val code: Int) : Event
        data class NetworkError(val throwable: Throwable) : Event
        data class Loading(val show: Boolean) : Event
        data class Message(val message: String) : Event
        data object Home : Event
    }
}

