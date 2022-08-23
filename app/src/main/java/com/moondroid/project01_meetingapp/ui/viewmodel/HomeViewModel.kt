package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.view.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    val showLoading = MutableLiveData<Boolean>()
    val groupsContent = SingleLiveEvent<BaseResponse>()
    val myGroupsContent = SingleLiveEvent<BaseResponse>()
    val interestResponse = SingleLiveEvent<BaseResponse>()
    val moimResponse = SingleLiveEvent<BaseResponse>()

    fun loadGroup() {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.loadGroup()
            }


            when (response) {
                is UseCaseResult.Success -> {
                    log("[HomeActivity], loadGroup , Response => ${response.data}")
                    showLoading.value = false
                    groupsContent.value = response.data
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

    fun getMyGroup(userId: String) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getMyGroup(userId)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    myGroupsContent.value = response.data
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

    fun updateInterest(id: String, interest: String) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.updateInterest(id, interest)
            }

            when (response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    interestResponse.value = response.data
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

    fun getMoim() {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.getMoim()
            }

            when(response) {
                is UseCaseResult.Success -> {
                    showLoading.value = false
                    moimResponse.value = response.data
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