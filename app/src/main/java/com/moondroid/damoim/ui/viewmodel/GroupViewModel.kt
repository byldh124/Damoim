package com.moondroid.damoim.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.damoim.model.User
import com.moondroid.damoim.network.Repository
import com.moondroid.damoim.network.SingleLiveEvent
import com.moondroid.damoim.network.UseCaseResult
import com.moondroid.damoim.utils.DMLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupViewModel(private val repository: Repository) : BaseViewModel() {
    val showLoading = MutableLiveData<Boolean>()
    val memberContent = SingleLiveEvent<ArrayList<User>>()

    fun loadMember(meetName: String) {
        showLoading.value = true

        launch {
            val response = withContext(Dispatchers.IO) {
                repository.loadMember(meetName)
            }
            showLoading.value = false

            when (response) {
                is UseCaseResult.Success -> {
                    if (response.data.code == 1000) {
                        val body = response.data.body.asJsonArray
                        val gson = Gson()
                        val member = gson.fromJson<ArrayList<User>>(body, object : TypeToken<ArrayList<User>>(){}.type)
                        memberContent.value = member
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