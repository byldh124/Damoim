package com.moondroid.project01_meetingapp.utils

import android.util.Log
import com.moondroid.project01_meetingapp.BuildConfig

open class DMLog protected constructor() {
    companion object {
        private val isDebug = BuildConfig.DEBUG

        private const val TAG = "다모임"

        fun d(msg: String) {
            if (isDebug) {
                Log.d(TAG, msg)
            }
        }

        fun e(msg: String) {
            if (isDebug) {
                Log.e(TAG, msg)
            }
        }
    }
}