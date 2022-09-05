package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class ProfileViewModel @Inject constructor(val repository: Repository) : BaseViewModel(){
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _myGroupsContent = SingleLiveEvent<BaseResponse>()
    val myGroupsContent: LiveData<BaseResponse> get() = _myGroupsContent

    fun getMyGroup(userId: String) {
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getMyGroup(userId)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _myGroupsContent.postValue(response.data)
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