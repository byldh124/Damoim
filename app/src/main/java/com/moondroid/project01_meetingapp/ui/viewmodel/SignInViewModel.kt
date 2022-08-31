package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_moondroid_project01_meetingapp_ui_viewmodel_SignInViewModel_HiltModules_KeyModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _signInResponse = SingleLiveEvent<BaseResponse>()
    val signInResponse: LiveData<BaseResponse> get() = _signInResponse

    private val _saltResponse = SingleLiveEvent<BaseResponse>()
    val saltResponse : LiveData<BaseResponse> get() = _saltResponse

    private val _kakaoSignInResponse = SingleLiveEvent<BaseResponse>()
    val kakaoSignInResponse: LiveData<BaseResponse> get() = _kakaoSignInResponse


    fun getSalt(id: String) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getSalt(id)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _saltResponse.postValue(response.data)
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

    fun signIn(body: JsonObject) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signIn(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _signInResponse.postValue(response.data)
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

    fun signInkakao(body: JsonObject) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signInKakao(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _kakaoSignInResponse.postValue(response.data)
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