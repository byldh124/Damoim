package com.moondroid.project01_meetingapp.utils.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.domain.model.Profile


object FBAnalyze {
    private lateinit var analytics: FirebaseAnalytics

    fun init(context: Context) {
        analytics = FirebaseAnalytics.getInstance(context)
    }

    fun setProperty(user: Profile) {
        analytics.setUserId(user.id)
    }

    fun logEvent(event: String) {
        analytics.logEvent(event, null)
    }

    fun logEvent(event: String, params: Bundle) {
        analytics.logEvent(event, params)
    }
}