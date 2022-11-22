package com.moondroid.project01_meetingapp.base

import androidx.lifecycle.ViewModel
import com.moondroid.project01_meetingapp.utils.NETWORK_NOT_CONNECTED
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import com.moondroid.project01_meetingapp.utils.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

// 반복을 막기 위해 abstract class 생성
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

    fun logException(e: Throwable) {
        DMCrash.getInstance()
            .log(e.message.toString())
        log(e.message.toString())
    }

    protected fun handleException(e: Throwable): Int {
        logException(e)
        return if (e is UnknownHostException) {
            NETWORK_NOT_CONNECTED
        } else {
            0
        }
    }
}
