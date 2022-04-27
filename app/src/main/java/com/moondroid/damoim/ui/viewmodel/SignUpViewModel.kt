package com.moondroid.damoim.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.moondroid.damoim.model.User
import com.moondroid.damoim.network.Repository
import com.moondroid.damoim.network.SingleLiveEvent
import com.moondroid.damoim.network.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(private val repository: Repository) : BaseViewModel()  {
    val showLoading = MutableLiveData<Boolean>()
    val signUpSuccess = SingleLiveEvent<Boolean>()
    val usableId = SingleLiveEvent<Boolean>()

    fun singUp(user:User){
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.signUp(user)
            }
            showLoading.value = false

            when (response) {
                is UseCaseResult.Success -> {
                    signUpSuccess.value = response.data.code == 1000
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }

            }
        }
    }

    fun checkId(id :String){
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO){
                repository.checkId(id)
            }
            showLoading.value = false

            when (response){
                is UseCaseResult.Success -> {
                    usableId.value = response.data.body
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