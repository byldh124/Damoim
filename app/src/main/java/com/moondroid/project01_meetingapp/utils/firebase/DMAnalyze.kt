package com.moondroid.project01_meetingapp.utils.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.utils.RequestParam

class DMAnalyze {
    companion object {
        @Volatile
        private var instance: FirebaseAnalytics? = null

        @JvmStatic
        fun init(context: Context): FirebaseAnalytics =
            instance ?: synchronized(this) {
                instance ?: FirebaseAnalytics.getInstance(context).also {
                    instance = it
                }
            }

        fun setProperty(user: User) {
            instance?.apply {
                setUserId(user.id)
                setUserProperty(RequestParam.ID, user.id)
                setUserProperty(RequestParam.NAME, user.name)
                setUserProperty(RequestParam.GENDER, user.gender)
            }
        }

        fun logEvent(event: String) {
            instance?.logEvent(event, null)
        }

        fun logEvent(event: String, params: Bundle) {
            instance?.logEvent(event, params)
        }
    }
}