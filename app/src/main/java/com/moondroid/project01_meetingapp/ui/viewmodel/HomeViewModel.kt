package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.model.GroupInfo
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.DMLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: Repository) : BaseViewModel() {

    val showLoading = MutableLiveData<Boolean>()
    val groupsContent = SingleLiveEvent<ArrayList<GroupInfo>>()
    val myGroupsContent = SingleLiveEvent<ArrayList<GroupInfo>>()

    fun loadGroup() {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.loadGroup()
            }
            showLoading.value = false

            when (response) {
                is UseCaseResult.Success -> {
                    if (response.data.code == 1000) {
                        val result = response.data.body.asJsonArray
                        val gson = GsonBuilder().create()
                        val groups = gson.fromJson<ArrayList<GroupInfo>>(
                            result,
                            object : TypeToken<ArrayList<GroupInfo>>() {}.type
                        )
                        groupsContent.value = groups
                    } else {
                        DMLog.e("code : " + response.data.code)
                    }
                }

                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }

            }
        }
    }

    fun loadMyGroup(userId: String) {
        showLoading.value = true
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.loadMyGroup(userId)
            }
            showLoading.value = false

            when (response) {
                is UseCaseResult.Success -> {
                    if (response.data.code == 1000) {
                        val result = response.data.body.asJsonArray
                        val gson = GsonBuilder().create()
                        val groups = gson.fromJson<ArrayList<GroupInfo>>(
                            result,
                            object : TypeToken<ArrayList<GroupInfo>>() {}.type
                        )
                        myGroupsContent.value = groups
                    } else {
                        DMLog.e("code : " + response.data.code)
                    }
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