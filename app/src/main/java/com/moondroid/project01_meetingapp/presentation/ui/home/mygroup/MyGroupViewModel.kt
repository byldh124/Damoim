package com.moondroid.project01_meetingapp.presentation.ui.home.mygroup

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.status.onError
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
class MyGroupViewModel @Inject constructor(private val getGroupUseCase: GetGroupUseCase) : BaseViewModel() {

    fun getMyGroup() {
        viewModelScope.launch {
            getGroupUseCase(ProfileHelper.profile.id, GroupType.MY_GROUP).collect { result ->
                result.onSuccess { update(it) }
                    .onError { logException(it) }
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

    private fun update(list: List<GroupItem>) = event(Event.Update(list))

    sealed class Event {
        data class Update(val list: List<GroupItem>) : Event()
    }
}