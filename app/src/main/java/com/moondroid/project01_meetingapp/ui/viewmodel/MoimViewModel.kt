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
import javax.inject.Inject

@HiltViewModel
class MoimViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _moimResponse = SingleLiveEvent<BaseResponse>()
    val moimResponse: LiveData<BaseResponse> get() = _moimResponse


    fun createMoim(body:JsonObject) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.createMoim(body)
            }

            _showLoading.postValue(false)
            when (response) {
                is UseCaseResult.Success -> {
                    _moimResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showError.postValue(response.errCode)
                }
                is UseCaseResult.Error -> {
                    handleException(response.exception)
                }
            }

        }
    }
}