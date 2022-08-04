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

    val userstatusKakao = MutableLiveData<Int>()
    val userInfoKakao = SingleLiveEvent<User>()

    val signInResponse = SingleLiveEvent<BaseResponse>()
    val saltResponse = SingleLiveEvent<BaseResponse>()

    val kakaoSignInResponse = SingleLiveEvent<BaseResponse>()

    fun getSalt(id: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getSalt(id)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    saltResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }

    fun signIn(body: JsonObject) {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signIn(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    signInResponse.value = response.data
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }

            }
        }
    }

    fun signInkakao(body: JsonObject) {
        launch {
            val response = withContext(Dispatchers.IO){
                repository.signInKakao(body)
            }
            when (response) {
                is UseCaseResult.Success -> {
                    kakaoSignInResponse.value = response.data
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