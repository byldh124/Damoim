package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.DMLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(private val repository: Repository) : BaseViewModel() {

    val showLoading = MutableLiveData<Boolean>()

    val signInResponse = SingleLiveEvent<BaseResponse>()
    val saltResponse = SingleLiveEvent<BaseResponse>()

    val kakaoSignInResponse = SingleLiveEvent<BaseResponse>()

    fun getSalt(id: String) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getSalt(id)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    saltResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    showLoading.value = false
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }

    fun signIn(body: JsonObject) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signIn(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    signInResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    showLoading.value = false
                    response.exception.message?.let {
                        logException(it)
                    }
                }

            }
        }
    }

    fun signInkakao(body: JsonObject) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signInKakao(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    kakaoSignInResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    showLoading.value = false
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }


    }
}