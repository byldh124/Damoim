package com.moondroid.project01_meetingapp.presentation.ui.home.mygroup

import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.damoim.domain.model.GroupInfo
import com.moondroid.damoim.domain.usecase.group.GroupUseCase
import com.moondroid.project01_meetingapp.utils.GroupType
import com.moondroid.project01_meetingapp.utils.ResponseCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGroupViewModel @Inject constructor(private val groupUseCase: GroupUseCase): BaseViewModel() {

    fun getMyGroup() {
        viewModelScope.launch {
            groupUseCase(GroupType.MY_GROUP).collect {result ->
                result
                    .onSuccess {
                        when (it.code) {
                            ResponseCode.SUCCESS -> {
                                val gson = GsonBuilder().create()
                                val list = gson.fromJson<ArrayList<GroupInfo>>(
                                    it.body.asJsonArray,
                                    object : TypeToken<ArrayList<GroupInfo>>() {}.type
                                )
                                update(list)
                            }
                        }
                    }
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

    private fun update(list: List<GroupInfo>) = event(Event.Update(list))

    sealed class Event{
        data class Update(val list: List<GroupInfo>) : Event()
    }
}