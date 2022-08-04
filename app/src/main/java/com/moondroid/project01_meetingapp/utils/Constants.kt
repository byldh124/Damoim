package com.moondroid.project01_meetingapp.utils

object Constants {
    const val DEFAULT_PROFILE_IMG = "http://moondroid.dothome.co.kr/damoim/userProfileImg/IMG_20210302153242unnamed.jpg"

    const val PREFS_NAME = "pref"

    object PrefKey {
        const val USER_INFO = "USER_INFO"
    }

    const val GROUP = "GROUP"

    const val ACTIVITY_TY = "ACTIVITY_TY"
    const val currentGroup = "currentGroup"

    object ResponseCode{
        const val SUCCESS = 1000
        const val FAIL = 2000
        const val NOT_EXIST = 2001
        const val ALREADY_EXIST = 2002
        const val INVALID_VALUE = 2003
        const val INACTIVE = 2004
    }


    object ActivityTy {
        const val SPLASH = 1
        const val SIGN_IN = 2
        const val SIGN_UP = 3
        const val HOME = 4
        const val GROUP = 5
        const val CREATE_GROUP = 6
    }

    object IntentParam{
        const val SEND_ACTIVITY = "SEND_ACTIVITY"
        const val INTEREST = "INTEREST"
        const val ICON_URL = " ICON_URL"
        const val LOCATION = "LOCATION"
    }
}