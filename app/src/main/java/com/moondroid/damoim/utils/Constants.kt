package com.moondroid.damoim.utils

object Constants {

    const val PREFS_NAME = "pref"

    object PrefKey {
        const val USER_ID = "USER_ID"
    }

    const val GROUP = "GROUP"

    const val ACTIVITY_TY = "ACTIVITY_TY"
    const val currentGroup = "currentGroup"


    object ActivityTy {
        const val SPLASH = 1
        const val HOME = 2
        const val GROUP = 3
        const val SIGN_IN = 4
        const val CREATE_GROUP = 5
    }
}