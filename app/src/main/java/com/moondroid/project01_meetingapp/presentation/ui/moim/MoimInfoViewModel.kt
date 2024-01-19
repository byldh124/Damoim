package com.moondroid.project01_meetingapp.presentation.ui.moim

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.moim.GetMoimMembersUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoimInfoViewModel @Inject constructor(
    private val moimMembersUseCase: GetMoimMembersUseCase
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun getMember(joinMember: String) {
        loading(true)
        viewModelScope.launch {
            moimMembersUseCase(joinMember).collect {result ->
                loading(false)
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

    sealed interface Event {
        data class Members(val list: List<Profile>) : Event
    }
}