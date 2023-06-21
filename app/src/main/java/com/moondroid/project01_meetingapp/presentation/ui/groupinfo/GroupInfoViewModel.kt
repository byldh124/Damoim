package com.moondroid.project01_meetingapp.presentation.ui.groupinfo

import com.moondroid.damoim.domain.usecase.group.GroupUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class GroupInfoViewModel @Inject constructor(

) : BaseViewModel() {

    fun updateGroup(
        body: Map<String, RequestBody>,
        thumb: MultipartBody.Part?,
        image: MultipartBody.Part?
    ) {
        //TODO()
    }
}