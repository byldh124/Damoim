package com.moondroid.project01_meetingapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.damoim.data.response.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val repository: Repository) : BaseViewModel() {
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _myGroupsContent = SingleLiveEvent<BaseResponse>()
    val myGroupsContent: LiveData<BaseResponse> get() = _myGroupsContent

    private val _blockResponse = SingleLiveEvent<BaseResponse>()
    val blockResponse: LiveData<BaseResponse> get() = _blockResponse

    fun blockUser(id: String, blockId: String) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.blockUser(id, blockId)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _blockResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showError.postValue(handleException(response.exception))
                }
            }
            _showLoading.postValue(false)
        }
    }

    fun getMyGroup(userId: String) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getMyGroup(userId)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _myGroupsContent.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showError.postValue(handleException(response.exception))
                }
            }
            _showLoading.postValue(false)
        }
    }
}