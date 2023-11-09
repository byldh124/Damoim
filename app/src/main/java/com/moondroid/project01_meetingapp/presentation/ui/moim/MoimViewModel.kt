package com.moondroid.project01_meetingapp.presentation.ui.moim

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.domain.model.MoimAddress
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.moim.CreateMoimUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.project01_meetingapp.utils.ProfileHelper
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoimViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val createMoimUseCase: CreateMoimUseCase,
) : BaseViewModel() {

    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val pay = MutableLiveData<String>()
    var address: MoimAddress? = null

    fun createMoim() {
        if (address == null) return

        if (date.value!!.isEmpty()) {
            context.toast(context.getString(R.string.error_empty_moim_date))
            return
        } else if (time.value!!.isEmpty()) {
            context.toast(context.getString(R.string.error_empty_moim_time))
            return
        } else {
            if (pay.value!!.isEmpty()) {
                pay.value = String.format("0%s", context.getString(R.string.cmn_won))
            } else {
                if (!pay.value!!.endsWith(context.getString(R.string.cmn_won))) {
                    pay.value += context.getString(R.string.cmn_won)
                }
            }
            viewModelScope.launch {
                createMoimUseCase(
                    DMApp.group.title,
                    address!!.address,
                    date.value!!,
                    time.value!!,
                    pay.value!!,
                    address!!.lat,
                    address!!.lng,
                    ProfileHelper.profile.id
                ).collect { result ->
                    result.onSuccess {
                        event(Event.Success)
                    }.onFail {
                        event(Event.Fail(it))
                    }.onError {
                        event(Event.Error(it))
                    }
                }
            }
        }
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun showDate() = event(Event.Date)
    fun showTime() = event(Event.Time)
    fun toLocation() = event(Event.Location)

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed interface Event {
        object Location : Event
        object Date : Event
        object Time : Event
        object Success : Event
        data class Fail(val code: Int) : Event
        data class Error(val throwable: Throwable) : Event
    }
}