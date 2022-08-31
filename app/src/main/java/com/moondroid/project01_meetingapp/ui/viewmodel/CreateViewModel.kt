package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.view.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(private val repository: Repository): BaseViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading : LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError : LiveData<Int> get() = _showError

    private val _createResponse = SingleLiveEvent<BaseResponse>()
    val createResponse : LiveData<BaseResponse> get() = _createResponse


    fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO){
                repository.createGroup(body, file)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _createResponse.postValue(response.data)
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