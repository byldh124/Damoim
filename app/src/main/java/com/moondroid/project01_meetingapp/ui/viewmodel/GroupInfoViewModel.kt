package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class GroupInfoViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _groupInfoResponse = SingleLiveEvent<BaseResponse>()
    val groupInfoResponse: LiveData<BaseResponse> get() = _groupInfoResponse

    fun updateGroup(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
        image: MultipartBody.Part?
    ) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.updateGroup(body, thumb, image)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _groupInfoResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showLoading.postValue(false)
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showLoading.postValue(false)
                    handleException(response.exception)
                }
            }
        }
    }
}