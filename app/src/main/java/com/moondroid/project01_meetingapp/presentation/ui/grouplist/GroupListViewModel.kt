package com.moondroid.project01_meetingapp.presentation.ui.grouplist

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetGroupUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val getGroupUseCase: GetGroupUseCase) : BaseViewModel() {

    fun getList(type: GroupType) {
        loading(true)
        viewModelScope.launch {
            getGroupUseCase(DMApp.profile.id, type).collect { result ->
                loading(false)
                result.onSuccess {
                    updateList(it)
                }.onFail {
                    event(Event.ServerError(it))
                }.onError {
                    event(Event.NetworkError(it))
                }
            }
        }
    }


    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun loading(b: Boolean) = event(Event.Loading(b))
    private fun updateList(list: List<GroupItem>) = event(Event.Update(list))

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed interface Event {
        data class Loading(val boolean: Boolean) : Event
        data class ServerError(val code: Int) : Event
        data class NetworkError(val throwable: Throwable) : Event
        data class Update(val list: List<GroupItem>) : Event
    }
}