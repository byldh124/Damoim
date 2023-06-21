package com.moondroid.project01_meetingapp.presentation.ui.signup

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.common.Extension.toast
import com.moondroid.damoim.common.Regex
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.data.api.response.onSuccess
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.damoim.domain.usecase.sign.SignUpUseCase
import com.moondroid.damoim.domain.usecase.profile.TokenUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileUseCase: ProfileUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val tokenUseCase: TokenUseCase
) : BaseViewModel() {

    val id = MutableLiveData<String>()                             // ID
    val pw = MutableLiveData<String>()                             // PW - 유효성 확인 및 해시값 생성에만 사용
    val pw2 = MutableLiveData<String>()                            // PW - 유효성 확인 및 해시값 생성에만 사용
    val name = MutableLiveData<String>()                           // 이름
    val gender = MutableLiveData<String>()                         // 성별
    val thumb = MutableLiveData<String>()                          // 썸네일 - 카카오 로그인이 아닌경우는 기본값으로 설정
    val birth = MutableLiveData<String>()                          // 생년월일
    val location = MutableLiveData<String>()                       // 관심지역
    val interest = MutableLiveData<String>()                       // 관심사
    val toAgree = MutableLiveData(false)

    var fromSocial = false

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun signUp() {
        try {
            if (!fromSocial && !id.value.toString().matches(Regex.ID)) {
                context.toast(R.string.error_id_mismatch)
            } else if (!fromSocial && !pw.value.toString().matches(Regex.PW)) {
                context.toast(R.string.error_password_mismatch)
            } else if (!fromSocial && pw.value != pw2.value) {
                context.toast(R.string.error_password_unchecked)
            } else if (!fromSocial && !name.value.toString().matches(Regex.NAME)
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
            e.logException()
        }
    }

    /**
     * 비밀번호 해시값 생성
     */
    private fun encryptPw() {
        try {
            val salt = getSalt()
            salt?.let {
                val hashPw = DMUtils.hashingPw(pw.value!!, it)

                val jsonObject = JsonObject()
                jsonObject.addProperty(RequestParam.ID, id.value)
                jsonObject.addProperty(RequestParam.HASH_PW, hashPw)
                jsonObject.addProperty(RequestParam.SALT, salt)
                jsonObject.addProperty(RequestParam.NAME, name.value)
                jsonObject.addProperty(RequestParam.BIRTH, birth.value)
                jsonObject.addProperty(RequestParam.GENDER, gender.value)
                jsonObject.addProperty(RequestParam.LOCATION, location.value)
                jsonObject.addProperty(RequestParam.INTEREST, interest.value)
                jsonObject.addProperty(RequestParam.THUMB, thumb.value)

                signUp(jsonObject)
            }

        } catch (e: Exception) {
            e.logException()
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

            DMUtils.byteToString(temp)
        } catch (e: Exception) {
            e.logException()
            null
        }
    }

    fun signUp(body: JsonObject) {
        loading(true)
        viewModelScope.launch {
            signUpUseCase(body).collect { result ->
                loading(false)
                result.onSuccess {
                    DMAnalyze.setProperty(it)
                    DMCrash.setProperty(it.id)
                    getMsgToken()

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

                debug("getMsgToken() , token => $token")
                updateToken(token)
            })
        } catch (e: Exception) {
            e.logException()
        }
    }


    /**
     * 토큰 등록
     * [토큰 미등록시에도 정상처리]
     */
    private fun updateToken(token: String) {
        try {
            val body = JsonObject()
            body.addProperty(RequestParam.ID, id.value)
            body.addProperty(RequestParam.TOKEN, token)


            viewModelScope.launch {
                tokenUseCase(body).collect {
                    loading(false)
                    home()
                }
            }
        } catch (e: Exception) {
            e.logException()
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
}

sealed class Event {
    class Loading(val show: Boolean) : Event()
    class Message(val message: String) : Event()
    object Home : Event()
}