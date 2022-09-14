package com.moondroid.project01_meetingapp.utils.firebase

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class DMCrash private constructor() {
    companion object {
        @Volatile
        private var instance: FirebaseCrashlytics? = null

        @JvmStatic
        fun getInstance(): FirebaseCrashlytics =
            instance ?: synchronized(this) {
                instance ?: FirebaseCrashlytics.getInstance().also {
                    instance = it
                }
            }

        fun setProperty(userId: String){
            instance?.setUserId(userId)
        }

        fun logException(exception: Exception) {
            try {
                if (instance == null) {
                    getInstance()
                }
                Log.e("[logException]", exception.message.toString())
                instance?.log(exception.message.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun logException(t: Throwable) {
            try {
                if (instance == null) {
                    getInstance()
                }
                Log.e("[logException]", t.message.toString())
                instance?.log(t.message.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}