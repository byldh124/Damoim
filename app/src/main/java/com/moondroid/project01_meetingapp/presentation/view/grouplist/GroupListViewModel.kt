package com.moondroid.project01_meetingapp.presentation.view.grouplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.usecase.group.GroupUseCase
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.GroupType
import com.moondroid.project01_meetingapp.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val groupUseCase: GroupUseCase, ) : BaseViewModel() {

    fun getList(type: GroupType) {
        viewModelScope.launch {
            groupUseCase(type).collect {result ->
                result.onSuccess {
                    log(it.toString())
                }.onError {

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