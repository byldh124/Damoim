package com.moondroid.project01_meetingapp.presentation.ui.grouplist

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetGroupUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val getGroupUseCase: GetGroupUseCase) : BaseViewModel() {

    fun getList(type: GroupType) {
        loading(true)
        viewModelScope.launch {
            getGroupUseCase(ProfileHelper.profile.id, type).collect { result ->
                loading(false)
                result.onSuccess { updateList(it) }
                    .onFail { serverError(it) }
                    .onError { networkError(it) }
            }
        }
    }


    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun updateList(list: List<GroupItem>) = event(Event.Update(list))

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed interface Event {
        data class Update(val list: List<GroupItem>) : Event
    }
}