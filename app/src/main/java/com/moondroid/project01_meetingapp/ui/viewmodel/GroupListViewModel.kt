package com.moondroid.project01_meetingapp.ui.viewmodel

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
    val showLoading = MutableLiveData<Boolean>()
    val favoriteResponse = SingleLiveEvent<BaseResponse>()
    val recentResponse = SingleLiveEvent<BaseResponse>()


    fun getFavorite(id: String){
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getFavorite(id)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    favoriteResponse.value = response.data
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

    fun getRecent(id: String){
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getRecent(id)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    recentResponse.value = response.data
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