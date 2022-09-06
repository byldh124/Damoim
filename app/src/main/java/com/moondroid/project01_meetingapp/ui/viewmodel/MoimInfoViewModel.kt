package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoimInfoViewModel @Inject constructor(private val repository: Repository): BaseViewModel() {

    private val _showLoading =  MutableLiveData<Boolean>()
    val showLoading : LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError : LiveData<Int> get() = _showError

    private val _memberResponse = SingleLiveEvent<BaseResponse>()
    val memberResponse : LiveData<BaseResponse> get() = _memberResponse

    private val _joinResponse = SingleLiveEvent<BaseResponse>()
    val joinResponse : LiveData<BaseResponse> get() = _joinResponse

    fun join(id: String, title: String, date: String){
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repository.joinInMoim(id, title, date)
            }
            _showLoading.postValue(false)
            when(response) {
                is UseCaseResult.Success -> {
                    _joinResponse.postValue(response.data)
                }

                is  UseCaseResult.Fail -> {
                    _showError.postValue(response.errCode)
                }

                is UseCaseResult.Error -> {
                    handleException(response.exception)
                }
            }
        }
    }

    fun getMember(joinMember: String){
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getMoimMember(joinMember)
            }

            _showLoading.postValue(false)
            when (response) {
                is UseCaseResult.Success -> {
                    _memberResponse.postValue(response.data)
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