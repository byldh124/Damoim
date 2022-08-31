package com.moondroid.project01_meetingapp.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.NETWORK_NOT_CONNECTED
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.net.UnknownHostException
import kotlin.contracts.Returns
import kotlin.coroutines.CoroutineContext

// 반복을 막기 위해 abstract class 생성
abstract class BaseViewModel : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        // 메모리 누수를 막기 위해 연결된 액티비티가 destroyed 될 때 job을 제거한다.
        job.cancel()
    }

    fun logException(msg: String){
        DMCrash.getInstance()
            .log(msg)
        DMLog.e(msg)
    }

    protected fun handleException(e: Throwable) : Int {
        e.message?.let {
            logException(it)
        }
        return if (e is UnknownHostException) {
            NETWORK_NOT_CONNECTED
        }  else {
            0
        }
    }

    protected fun showErrorToast() {

    }

}
