package com.moondroid.project01_meetingapp.presentation.ui.moim

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.domain.model.MoimAddress

import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoimViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileUseCase: ProfileUseCase,
) : BaseViewModel() {

    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val pay = MutableLiveData<String>()
    var address : MoimAddress? = null


    fun createMoim(body: JsonObject) {

    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun showDate() = event(Event.Date)
    fun showTime() = event(Event.Time)
    fun toLocation() = event(Event.Location)
    fun save() {
        try {
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

                viewModelScope.launch(Dispatchers.IO) {
                    profileUseCase().collect { result ->
                        result.onSuccess {
                            val joinMember = Gson().toJson(arrayOf(it.id))
                            val body = JsonObject()
                            body.addProperty(RequestParam.TITLE, DMApp.group.title)
                            body.addProperty(RequestParam.ADDRESS, address!!.address)
                            body.addProperty(RequestParam.DATE, date.value!!)
                            body.addProperty(RequestParam.TIME, time.value!!)
                            body.addProperty(RequestParam.PAY, pay.value!!)
                            body.addProperty(RequestParam.LAT, address!!.lat)
                            body.addProperty(RequestParam.LNG, address!!.lng)
                            body.addProperty(RequestParam.JOIN_MEMBER, joinMember)

                            createMoim(body)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        object Location : Event()
        object Date : Event()
        object Time : Event()
    }
}