package com.moondroid.project01_meetingapp.presentation.ui.profile

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getGroupUseCase: GetGroupUseCase
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun blockUser(blockId: String) {
        //TODO
    }

    fun getMyGroup(id: String) {
        viewModelScope.launch {
            getGroupUseCase(id, GroupType.MY_GROUP).collect {result ->
                result.onSuccess {
                    event(Event.UpdateGroup(it))
                }.onFail {

                }.onError {

                }
            }
        }
    }

    sealed interface Event {
        data class UpdateGroup(val list: List<GroupItem>): Event
    }
}