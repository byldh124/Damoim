package com.moondroid.project01_meetingapp.utils


const val DEFAULT_PROFILE_IMG =
    "http://moondroid.dothome.co.kr/damoim-test/thumbs/IMG_20210302153242unnamed.jpg"

const val PREFS_NAME = "pref"

const val NETWORK_NOT_CONNECTED = -200

object PrefKey {
    const val USER_INFO = "USER_INFO"
}

const val GROUP = "GROUP"

const val currentGroup = "currentGroup"

object ResponseCode {
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
    const val GROUP_LIST = 7
    const val CREATE = 8
}

object RequestParam {
    /** 유저 관련 **/
    const val ID = "id"
    const val HASH_PW = "hashPw"
    const val NAME = "name"
    const val THUMB = "thumb"
    const val SALT = "salt"
    const val LOCATION = "location"
    const val INTEREST = "interest"
    const val BIRTH = "birth"
    const val GENDER = "gender"
    const val MESSAGE = "message"
    const val TOKEN = "token"

    /** 그룹 관련 **/
    const val TITLE = "title"
    const val PURPOSE = "purpose"
    const val IMAGE = "image"
    const val INFORMATION = "information"
    const val MASTER_ID = "materId"

    /** 일반 **/
    const val LAST_TIME = "lastTime"
    const val ACTIVE = "active"
}

object IntentParam {
    const val ACTIVITY = "ACTIVITY"
    const val SEND_ACTIVITY = "SEND_ACTIVITY"
    const val INTEREST = "INTEREST"
    const val INTEREST_ICON = "INTEREST_ICON"
    const val ICON_URL = " ICON_URL"
    const val LOCATION = "LOCATION"
    const val TYPE = "TYPE"
}


object GroupListType {
    const val FAVORITE = 1
    const val RECENT = 2
}
