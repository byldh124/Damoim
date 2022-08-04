package com.moondroid.project01_meetingapp.ui.viewmodel

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(private val repository: Repository) : BaseViewModel() {
    val signUpResponse = SingleLiveEvent<BaseResponse>()
    val tokenResponse = SingleLiveEvent<BaseResponse>()

    fun signUp(body: JsonObject) {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signUp(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    signUpResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }

            }
        }
    }

    fun updateToken(body: JsonObject) {
        launch {
            val response = withContext(Dispatchers.IO){
                repository.updateToken(body)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    tokenResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }
}