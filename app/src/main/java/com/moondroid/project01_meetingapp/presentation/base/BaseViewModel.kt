package com.moondroid.project01_meetingapp.presentation.base

import androidx.lifecycle.ViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

    open fun <T> event(event: MutableEventFlow<T>) {

    }
}
