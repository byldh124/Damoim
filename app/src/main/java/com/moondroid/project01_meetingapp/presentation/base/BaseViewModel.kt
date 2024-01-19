package com.moondroid.project01_meetingapp.presentation.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        // 메모리 누수를 막기 위해 연결된 액티비티가 destroyed 될 때 job 제거
        job.cancel()
    }

    private var _commonEvent = MutableEventFlow<Event>()
    val commonEvent = _commonEvent.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _commonEvent.emit(event)
        }
    }

    protected fun loading(isLoading: Boolean) {
        event(Event.Loading(isLoading))
    }

    protected fun toast(message: String) {
        event(Event.Toast(message))
    }

    protected fun networkError(throwable: Throwable, onClick: (Context) -> Unit = {}) {
        event(Event.NetworkError(throwable, onClick))
    }

    protected fun serverError(code: Int, onClick: (Context) -> Unit = {}) {
        event(Event.ServerError(code, onClick))
    }

    protected fun message(message: String, onClick: (Context) -> Unit = {}) {
        event(Event.Message(message, onClick))
    }

    sealed interface Event {
        data class Loading(val isLoading: Boolean) : Event
        data class Message(val message: String, val onClick: (Context) -> Unit) : Event
        data class NetworkError(val throwable: Throwable, val onClick: (Context) -> Unit) : Event
        data class ServerError(val code: Int, val onClick: (Context) -> Unit) : Event
        data class Toast(val message: String) : Event
    }
}


