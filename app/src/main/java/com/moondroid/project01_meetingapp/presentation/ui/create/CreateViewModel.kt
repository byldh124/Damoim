package com.moondroid.project01_meetingapp.presentation.ui.create

import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor() : BaseViewModel() {
    fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ) {
        //TODO()
    }

}