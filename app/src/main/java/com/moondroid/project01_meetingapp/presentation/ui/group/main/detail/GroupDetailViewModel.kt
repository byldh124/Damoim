package com.moondroid.project01_meetingapp.presentation.ui.group.main.detail

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetMembersUseCase
import com.moondroid.damoim.domain.usecase.group.JoinGroupUseCase
import com.moondroid.damoim.domain.usecase.moim.GetMoimsUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val getMoimUseCase: GetMoimsUseCase,
    private val getMembersUseCase: GetMembersUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
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

    @SuppressLint("SimpleDateFormat")
    fun getMoim(title: String) {
        viewModelScope.launch {
            getMoimUseCase(title).collect { result ->
                result.onSuccess { list ->
                    val finalList = list.filter {
                        val format = SimpleDateFormat("yyyy.MM.dd")
                        val date = format.parse(it.date)
                        !(date!!.before(Date()))
                    }
                    event(Event.Moims(finalList))
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
            joinGroupUseCase(ProfileHelper.profile.id, title).collect { result ->
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