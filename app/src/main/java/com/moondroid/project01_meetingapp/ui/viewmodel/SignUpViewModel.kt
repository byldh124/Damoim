package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading : LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _signUpResponse = SingleLiveEvent<BaseResponse>()
    val signUpResponse : LiveData<BaseResponse> get() = _signUpResponse

    private val _tokenResponse = SingleLiveEvent<BaseResponse>()
    val tokenResponse : LiveData<BaseResponse> get() = _tokenResponse

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
}