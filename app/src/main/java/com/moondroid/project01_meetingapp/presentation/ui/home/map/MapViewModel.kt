package com.moondroid.project01_meetingapp.presentation.ui.home.map

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.moim.GetMoimsUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMoimsUseCase: GetMoimsUseCase,
) : BaseViewModel() {
    fun getMoim() {
        viewModelScope.launch {
            getMoimsUseCase().collect { result ->
                result.onSuccess { update(it) }
                    .onError { networkError(it) }
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