package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.moondroid.project01_meetingapp.BuildConfig
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.DMLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(private val repository: Repository) : BaseViewModel() {

    val showLoading = MutableLiveData<Boolean>()
    val appCheck = SingleLiveEvent<Int>()

    fun checkAppVersion(
        packageName :String,
        versionCode: Int,
        versionName: String
    ) {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.checkAppVersion(
                    packageName,versionCode, versionName
                )
            }

            when (response) {
                is UseCaseResult.Success -> {
                    appCheck.value = response.data.code
                    DMLog.e("[SplashViewModel] , checkAppVersion Response Message = ${response.data.msg}")
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