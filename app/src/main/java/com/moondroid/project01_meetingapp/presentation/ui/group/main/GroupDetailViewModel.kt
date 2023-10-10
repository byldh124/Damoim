package com.moondroid.project01_meetingapp.presentation.ui.group.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetFavorUseCase
import com.moondroid.damoim.domain.usecase.group.GetMembersUseCase
import com.moondroid.damoim.domain.usecase.group.GetMoimsUseCase
import com.moondroid.damoim.domain.usecase.group.JoinUseCase
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
class GroupDetailViewModel @Inject constructor(
    private val getMoimUseCase: GetMoimsUseCase,
    private val getMembersUseCase: GetMembersUseCase,
    private val joinUseCase: JoinUseCase,
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun serverError(code: Int) = event(Event.ServerError(code))
    private fun networkError(throwable: Throwable) = event(Event.NetworkError(throwable))

    fun getMoim(title: String) {
        viewModelScope.launch {
            getMoimUseCase(title).collect { result ->
                result.onSuccess {
                    event(Event.Moims(it))
                }.onError {
                    networkError(it)
                }.onFail {
                    serverError(it)
                }
            }
        }
    }

    fun loadMember(title: String) {
        viewModelScope.launch {
            getMembersUseCase(title).collect { result ->
                result.onSuccess {
                    event(Event.Members(it))
                }.onFail {
                    serverError(it)
                }.onError {
                    networkError(it)
                }
            }
        }
    }

    fun join(title: String) {
        viewModelScope.launch {
            joinUseCase(DMApp.profile.id, title).collect { result ->
                result.onSuccess {
                    event(Event.Join)
                }.onFail {
                    serverError(it)
                }.onError {
                    networkError(it)
                }
            }
        }
    }

    sealed interface Event {
        data class Moims(val list: List<MoimItem>) : Event
        data class Members(val list: List<Profile>) : Event
        data class ServerError(val code: Int) : Event
        data class NetworkError(val throwable: Throwable) : Event
        object Join : Event
    }
}