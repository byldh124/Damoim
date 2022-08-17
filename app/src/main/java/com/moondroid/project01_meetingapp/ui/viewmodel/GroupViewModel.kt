package com.moondroid.project01_meetingapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupViewModel(private val repository: Repository) : BaseViewModel() {
    val showLoading = MutableLiveData<Boolean>()
    val memberResponse = SingleLiveEvent<BaseResponse>()

    fun loadMember(title: String) {
        showLoading.value = true

        launch {
            val response = withContext(Dispatchers.IO) {
                repository.loadMember(title)
            }
            showLoading.value = false

            when (response) {
                is UseCaseResult.Success -> {
                    memberResponse.value = response.data
                    /*if (response.data.code == 1000) {
                        val body = response.data.body.asJsonArray
                        val gson = Gson()
                        val member = gson.fromJson<ArrayList<User>>(body, object : TypeToken<ArrayList<User>>(){}.type)
                        memberResponse.value = member
                    }*/
                }
                is UseCaseResult.Error -> {
                    response.exception.message?.let {
                        logException(it)
                    }
                }
            }
        }
    }

    fun checkMyGroup(title:String, id: String){


    }
}