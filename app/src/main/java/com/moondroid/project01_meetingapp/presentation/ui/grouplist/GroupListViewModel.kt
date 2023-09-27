package com.moondroid.project01_meetingapp.presentation.ui.grouplist

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.GetGroupUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(private val getGroupUseCase: GetGroupUseCase) : BaseViewModel() {

    fun getList(type: GroupType) {
        viewModelScope.launch {
            getGroupUseCase(type).collect { result ->
                result.onSuccess {
                    updateList(it)
                }.onError {
                    logException(it)
                }
            }
        }
    }


    private val _eventFlow = MutableEventFlow<GroupListEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun loading(b: Boolean) = event(GroupListEvent.Loading(b))
    private fun updateList(list: List<GroupItem>) = event(GroupListEvent.Update(list))

    private fun event(groupListEvent: GroupListEvent) {
        viewModelScope.launch {
            _eventFlow.emit(groupListEvent)
        }
    }

    sealed class GroupListEvent {
        data class Loading(val boolean: Boolean) : GroupListEvent()
        data class Error(val code: Int) : GroupListEvent()
        data class Update(val list: List<GroupItem>) : GroupListEvent()
    }
}