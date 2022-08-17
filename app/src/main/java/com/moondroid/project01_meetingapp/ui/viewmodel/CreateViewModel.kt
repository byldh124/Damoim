package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateViewModel(private val repository: Repository): BaseViewModel() {
    val showLoading = MutableLiveData<Boolean>()
    val createResponse = SingleLiveEvent<BaseResponse>()


    fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO){
                repository.createGroup(body, file)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    createResponse.value = response.data
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