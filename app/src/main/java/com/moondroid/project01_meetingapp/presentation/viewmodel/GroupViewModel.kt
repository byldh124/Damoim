package com.moondroid.project01_meetingapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _moimResponse = SingleLiveEvent<BaseResponse>()
    val moimResponse: LiveData<BaseResponse> get() = _moimResponse

    private val _memberResponse = SingleLiveEvent<BaseResponse>()
    val memberResponse: LiveData<BaseResponse> get() = _memberResponse

    private val _recentResponse = SingleLiveEvent<BaseResponse>()
    val recentResponse: LiveData<BaseResponse> get() = _recentResponse

    private val _joinResponse = SingleLiveEvent<BaseResponse>()
    val joinResponse: LiveData<BaseResponse> get() = _joinResponse

    private val _favorResponse = SingleLiveEvent<BaseResponse>()
    val favorResponse: LiveData<BaseResponse> get() = _favorResponse

    private val _saveFavorResponse = SingleLiveEvent<BaseResponse>()
    val saveFavorResponse: LiveData<BaseResponse> get() = _saveFavorResponse

    fun getMoim(title: String) {
        _showLoading.postValue(false)

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repository.getMoim(title)
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
                    _showError.postValue(handleException(response.exception))
                }
            }
        }
    }

    fun loadMember(title: String) {
        _showLoading.postValue(true)

        launch {
            val response = withContext(Dispatchers.IO) {
                repository.loadMember(title)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _memberResponse.postValue(response.data)
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

    fun saveRecent(id: String, title: String, lastTime: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.saveRecent(id, title, lastTime)
            }

            when (response) {
                is UseCaseResult.Success -> _recentResponse.postValue(response.data)

                is UseCaseResult.Fail -> {
                    DMCrash.logException(Exception("Exception on save recent error code : ${response.errCode}"))
                }

                is UseCaseResult.Error -> {
                    DMCrash.logException(response.exception as Exception)
                }
            }
        }
    }

    fun join(id: String, title: String) {
        _showLoading.postValue(true)

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.join(id, title)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _joinResponse.postValue(response.data)
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

    fun getFavor(id: String, title: String) {
        _showLoading.postValue(true)

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getFavor(id, title)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _favorResponse.postValue(response.data)
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

    fun saveFavor(id: String, title: String, active: Boolean) {
        _showLoading.postValue(true)

        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.saveFavor(id, title, active)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _showLoading.postValue(false)
                    _saveFavorResponse.postValue(response.data)
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