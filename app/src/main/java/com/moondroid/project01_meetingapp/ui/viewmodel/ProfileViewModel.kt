package com.moondroid.project01_meetingapp.ui.viewmodel

import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.DMLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    val profileResponse = SingleLiveEvent<BaseResponse>()

    fun updateProfile(body: Map<String, RequestBody>, file: MultipartBody.Part?) {
        launch {
            val response = withContext(Dispatchers.IO){
                repository.updateProfile(body, file)
            }

            when (response){
                is UseCaseResult.Success -> {
                    profileResponse.value = response.data

                    DMLog.e("updateProfile Response ${response.data}")
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }
}