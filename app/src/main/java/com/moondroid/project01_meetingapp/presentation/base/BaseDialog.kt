package com.moondroid.project01_meetingapp.presentation.base

import android.app.Dialog
import android.content.Context
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.R

open class BaseDialog(context: Context) : Dialog(context, R.style.DialogTheme) {
    fun exit() {
        if (isShowing) super.cancel()
    }
}