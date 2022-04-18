package com.moondroid.damoim.utils

import android.util.Log

open class DMLog protected constructor() {
    companion object {
        private val TAG = "다모임"

        fun d(msg: String) {
            Log.d(TAG, msg)
        }

        fun e(msg: String) {
            Log.e(TAG, msg)
        }

        fun i(msg: String) {
            Log.i(TAG, msg)
        }

        fun v(msg: String) {
            Log.v(TAG, msg)
        }
    }

}