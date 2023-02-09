package com.moondroid.project01_meetingapp.presentation.view.moim

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.model.Address
import com.moondroid.project01_meetingapp.domain.usecase.user.UserUseCase
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.RequestParam
import com.moondroid.project01_meetingapp.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoimViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userUseCase: UserUseCase,
    private val repository: Repository
) : BaseViewModel() {

    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val pay = MutableLiveData<String>()
    var address : Address? = null


    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _moimResponse = SingleLiveEvent<BaseResponse>()
    val moimResponse: LiveData<BaseResponse> get() = _moimResponse


    fun createMoim(body: JsonObject) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.createMoim(body)
            }

            _showLoading.postValue(false)
            when (response) {
                is UseCaseResult.Success -> {
                    _moimResponse.postValue(response.data)
                }

                is UseCaseResult.Fail -> {
                    _showError.postValue(response.errCode)
                }
                is UseCaseResult.Error -> {
                    handleException(response.exception)
                }
            }

        }
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
                    userUseCase().collect { result ->
                        result.onSuccess {
                            val joinMember = Gson().toJson(arrayOf(it.id))
                            val body = JsonObject()
                            body.addProperty(RequestParam.TITLE, DMApp.group.title)
                            body.addProperty(RequestParam.ADDRESS, address!!.address)
                            body.addProperty(RequestParam.DATE, date.value!!)
                            body.addProperty(RequestParam.TIME, time.value!!)
                            body.addProperty(RequestParam.PAY, pay.value!!)
                            body.addProperty(RequestParam.LAT, address!!.latLng.latitude)
                            body.addProperty(RequestParam.LNG, address!!.latLng.longitude)
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