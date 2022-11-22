package com.moondroid.project01_meetingapp.presentation.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    val id = MutableLiveData<String?>()                             // ID
    val pw = MutableLiveData<String?>()                             // PW - 유효성 확인 및 해시값 생성에만 사용
    val pw2 = MutableLiveData<String?>()                            // PW - 유효성 확인 및 해시값 생성에만 사용
    val name = MutableLiveData<String?>()                           // 이름
    val gender = MutableLiveData<String?>()                         // 성별
    val thumb = MutableLiveData<String?>()                          // 썸네일 - 카카오 로그인이 아닌경우는 기본값으로 설정
    val birth = MutableLiveData<String?>()                          // 생년월일
    val location = MutableLiveData<String?>()                       // 관심지역
    val interest = MutableLiveData<String?>()                       // 관심사

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

    fun signUp(body: JsonObject) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signUp(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _signUpResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showLoading.postValue(false)
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showLoading.postValue(false)
                    _showError.postValue(handleException(response.exception))
                }

            }
        }
    }

    fun updateToken(body: JsonObject) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.updateToken(body)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _tokenResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showLoading.postValue(false)
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showLoading.postValue(false)
                    _showError.postValue(handleException(response.exception))
                }
            }
        }
    }

    private fun loading(b: Boolean) = event(Event.Loading(b))
    fun message(msg: String) = event(Event.Message(msg))
    fun toast(resId: Int) = event(Event.Toast(resId))
    private fun home() = event(Event.Home)

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
}