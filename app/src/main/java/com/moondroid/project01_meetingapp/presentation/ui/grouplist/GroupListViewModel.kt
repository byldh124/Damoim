package com.moondroid.project01_meetingapp.presentation.ui.grouplist

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Constants
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onNetworkError
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GroupUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val groupUseCase: GroupUseCase, ) : BaseViewModel() {

    fun getList(type: GroupType) {
        viewModelScope.launch {
            groupUseCase(type).collect {result ->
                result.onSuccess {
                    debug(it.toString())
                }.onError {
                    it.logException()
                }
            }
        }
    }


    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun loading(b: Boolean) = event(Event.Loading(b))

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class Loading(val boolean: Boolean) : Event()
        data class Error(val code: Int) : Event()
    }
}