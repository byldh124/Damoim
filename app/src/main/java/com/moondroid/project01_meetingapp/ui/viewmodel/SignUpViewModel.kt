package com.moondroid.project01_meetingapp.ui.viewmodel

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
    val showLoading = MutableLiveData<Boolean>()
    val signUpResponse = SingleLiveEvent<BaseResponse>()
    val tokenResponse = SingleLiveEvent<BaseResponse>()

    fun signUp(body: JsonObject) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signUp(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    signUpResponse.value = response.data
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

    fun updateToken(body: JsonObject) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.updateToken(body)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    tokenResponse.value = response.data
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