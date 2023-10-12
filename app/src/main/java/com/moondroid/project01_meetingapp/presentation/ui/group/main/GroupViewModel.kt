package com.moondroid.project01_meetingapp.presentation.ui.group.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Extension
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetFavorUseCase
import com.moondroid.damoim.domain.usecase.group.SaveRecentUseCase
import com.moondroid.damoim.domain.usecase.group.SetFavorUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val saveRecentUseCase: SaveRecentUseCase,
    private val getFavorUseCase: GetFavorUseCase,
    private val setFavorUseCase: SetFavorUseCase
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    init {
        saveRecent(DMApp.group.title, System.currentTimeMillis().toString())
        getFavor(DMApp.group.title)
    }

    private fun serverError(code: Int) = event(Event.ServerError(code))
    private fun networkError(throwable: Throwable) = event(Event.NetworkError(throwable))

    val favor = MutableLiveData(false)
    private var favorChanging = false

    fun toggleFavor() {
        if (!favorChanging) {
            favorChanging = true
            saveFavor(DMApp.group.title)
        }
    }

    private fun saveRecent(title: String, lastTime: String) {
        viewModelScope.launch {
            saveRecentUseCase(DMApp.profile.id, title, lastTime).collect{}
        }
    }

    private fun getFavor(title: String) {
        viewModelScope.launch {
            getFavorUseCase(DMApp.profile.id, title).collect { result ->
                result.onSuccess {
                    favor.value = it
                }.onFail {
                    serverError(it)
                }.onError {
                    networkError(it)
                }
            }
        }
    }

    private fun saveFavor(title: String) {
        val sendFavor = !favor.value!!
        viewModelScope.launch {
            setFavorUseCase(DMApp.profile.id, title, sendFavor).collect { result ->
                result.onSuccess {
                    favor.value = sendFavor
                    favorChanging = false
                }.onError {
                    networkError(it)
                }.onFail {
                    serverError(it)
                }
            }
        }
    }

    sealed interface Event {
        data class ServerError(val code: Int) : Event
        data class NetworkError(val throwable: Throwable) : Event
    }
}