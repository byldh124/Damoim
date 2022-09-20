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
class SplashViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _appCheckResponse = SingleLiveEvent<BaseResponse>()
    val appCheckResponse: LiveData<BaseResponse> get() = _appCheckResponse

    fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ) {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.checkAppVersion(
                    packageName, versionCode, versionName
                )
            }

            when (response) {
                is UseCaseResult.Success -> {
                    _appCheckResponse.postValue(response.data)
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
}