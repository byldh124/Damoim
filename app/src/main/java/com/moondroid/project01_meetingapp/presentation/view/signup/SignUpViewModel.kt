package com.moondroid.project01_meetingapp.presentation.view.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.usecase.sign.SignUpUseCase
import com.moondroid.project01_meetingapp.domain.usecase.user.TokenUseCase
import com.moondroid.project01_meetingapp.domain.usecase.user.UserUseCase
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
    private val userUseCase: UserUseCase,
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

    var fromKakao = false

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _signUpResponse = SingleLiveEvent<BaseResponse>()
    val signUpResponse: LiveData<BaseResponse> get() = _signUpResponse

    private val _tokenResponse = SingleLiveEvent<BaseResponse>()
    val tokenResponse: LiveData<BaseResponse> get() = _tokenResponse

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun signUp() {
        try {
            if (!fromKakao && !id.value.toString().matches(Regex.ID)) {
                context.toast(R.string.error_id_mismatch)
            } else if (!fromKakao && !pw.value.toString().matches(Regex.PW)) {
                context.toast(R.string.error_password_mismatch)
            } else if (!fromKakao && pw.value != pw2.value) {
                context.toast(R.string.error_password_unchecked)
            } else if (!fromKakao && !name.value.toString().matches(Regex.NAME)
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

            DMUtils.byteToString(temp)
        } catch (e: Exception) {
            logException(e)
            null
        }
    }

    fun signUp(body: JsonObject) {
        loading(true)
        viewModelScope.launch {
            signUpUseCase(body).collect { result ->
                loading(false)
                result.onSuccess {
                    when (it.code) {
                        ResponseCode.SUCCESS -> {
                            context.toast(R.string.alm_sign_up_success)
                            val user = Gson().fromJson(it.body, User::class.java)
                            userUseCase(user)
                            DMAnalyze.setProperty(user)
                            DMCrash.setProperty(user.id)
                            getMsgToken()
                        }

                        ResponseCode.ALREADY_EXIST -> {
                            context.toast(R.string.error_id_already_exist)
                        }

                    }
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

                log("getMsgToken() , token => $token")
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
            logException(e)
        }
    }

    private fun loading(b: Boolean) = event(Event.Loading(b))
    fun message(msg: String) = event(Event.Message(msg))
    private fun home() = event(Event.Home)
    fun showDate() = event(Event.Date)
    fun toLocation() = event(Event.Location)
    fun toInterest() = event(Event.Interest)
    fun useTerm() = event(Event.UseTerm)
    fun privacy() = event(Event.Privacy)

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}

sealed class Event {
    class Loading(val show: Boolean) : Event()
    class Message(val message: String) : Event()
    object UseTerm : Event()
    object Privacy : Event()
    object Date : Event()
    object Interest : Event()
    object Location : Event()
    object Home : Event()
}