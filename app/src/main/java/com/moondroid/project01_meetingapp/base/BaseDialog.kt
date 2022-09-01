package com.moondroid.project01_meetingapp.base

import android.app.Dialog
import android.content.Context
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash

open class BaseDialog : Dialog {
    constructor(context: Context) : super(context, R.style.DialogTheme) {

    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {

    }

    fun logException(e: Exception) {
        DMCrash.logException(e)
    }
}