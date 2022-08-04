package com.moondroid.project01_meetingapp.utils.firebase

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

        fun logException(exception: Exception) {
            if (instance == null){
                getInstance()
            }
            instance?.log(exception.message.toString())
        }
    }
}