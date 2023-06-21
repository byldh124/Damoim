package com.moondroid.project01_meetingapp.presentation.ui.setting

import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(

) : BaseViewModel() {

    fun updateProfile(body: Map<String, RequestBody>, file: MultipartBody.Part?) {
        //TODO()
    }
}