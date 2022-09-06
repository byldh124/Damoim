package com.moondroid.project01_meetingapp.base

import android.app.Dialog
import android.content.Context
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash

open class BaseDialog(context: Context) : Dialog(context, R.style.DialogTheme) {

    fun logException(e: Exception) {
        DMCrash.logException(e)
    }
}