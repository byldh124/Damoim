package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError : LiveData<Int> get() = _showError

    private val _deleteRoomResponse = SingleLiveEvent<Boolean>()
    val deleteRoomResponse: LiveData<Boolean> get() = _deleteRoomResponse

    fun deleteRoom(user: User) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repository.deleteUserR(user)
            }

            _showLoading.postValue(false)
            _deleteRoomResponse.postValue(response)
        }
    }
}