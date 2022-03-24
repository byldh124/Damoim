package com.moondroid.damoim.utils.view

import android.app.Activity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.lang.Exception


fun Activity.logException(exception : Exception){
    FirebaseCrashlytics
        .getInstance()
        .log(exception.message.toString())
}

