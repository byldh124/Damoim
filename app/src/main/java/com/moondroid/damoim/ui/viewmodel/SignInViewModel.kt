package com.moondroid.damoim.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.damoim.model.GroupInfo
import com.moondroid.damoim.model.User
import com.moondroid.damoim.network.Repository
import com.moondroid.damoim.network.SingleLiveEvent
import com.moondroid.damoim.network.UseCaseResult
import com.moondroid.damoim.utils.DMLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(private val repository: Repository) : BaseViewModel() {

    val showLoading = MutableLiveData<Boolean>()
    val userstatus = MutableLiveData<Int>()
    val userInfo = SingleLiveEvent<User>()

    fun getUserInfo(userId: String) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.login(userId)
            }
            showLoading.value = false

            when (response) {
                is UseCaseResult.Success -> {
                    val code = response.data.code

                    if (response.data.code == 1000) {
                        val result = response.data.body.asJsonObject
                        userInfo.value = Gson().fromJson(result, User::class.java)
                    } else {
                        userstatus.value = code
                    }
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