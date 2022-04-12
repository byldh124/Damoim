package com.moondroid.damoim.utils.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics

class DMCrash private constructor() {

    companion object {
        @Volatile private var instance: FirebaseCrashlytics? = null

        @JvmStatic fun getInstance(): FirebaseCrashlytics =
            instance ?: synchronized(this) {
                instance ?: FirebaseCrashlytics.getInstance().also {
                    instance = it
                }
            }
    }


    fun logException(exception :Exception){
        instance!!.log(exception.message.toString())
    }

}