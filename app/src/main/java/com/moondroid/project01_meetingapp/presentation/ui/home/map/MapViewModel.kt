package com.moondroid.project01_meetingapp.presentation.ui.home.map

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.data.api.response.onSuccess
import com.moondroid.damoim.domain.usecase.group.MoimUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val moimUseCase: MoimUseCase
) : BaseViewModel() {
    fun getMoim() {
        viewModelScope.launch {
            moimUseCase().collect { result ->
                result.onSuccess { update(it) }
                    .onError { it.logException() }
            }
        }
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun update(list: List<MoimItem>) = event(Event.Update(list))


    sealed class Event {
        data class Update(val list: List<MoimItem>) : Event()
    }
}