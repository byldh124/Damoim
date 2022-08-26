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
class GroupListViewModel @Inject constructor(private val repository: Repository) : BaseViewModel(){
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading : LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError : LiveData<Int> get() = _showError

    private val _favoriteResponse = SingleLiveEvent<BaseResponse>()
    val favoriteResponse : LiveData<BaseResponse> get() = _favoriteResponse

    private val _recentResponse = SingleLiveEvent<BaseResponse>()
    val recentResponse: LiveData<BaseResponse> get() = _recentResponse


    fun getFavorite(id: String){
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getFavorite(id)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _favoriteResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showLoading.postValue(false)
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }

    fun getRecent(id: String){
        _showLoading.postValue(true)
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getRecent(id)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _recentResponse.value = response.data
                }

                is UseCaseResult.Fail -> {
                    _showLoading.postValue(false)
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    _showLoading.postValue(false)
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }
}