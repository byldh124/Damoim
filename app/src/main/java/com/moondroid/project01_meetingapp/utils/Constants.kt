package com.moondroid.project01_meetingapp.utils


const val DEFAULT_PROFILE_IMG =
    "http://moondroid.dothome.co.kr/damoim/thumbs/IMG_20210302153242unnamed.jpg"

const val PREFS_NAME = "pref"

const val NETWORK_NOT_CONNECTED = -200

object PrefKey {
    const val USER_INFO = "USER_INFO"
}

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
    const val CREATE = 6
    const val GROUP_LIST = 7
    const val GROUP_INFO = 8
    const val MOIM = 9
    const val INTEREST = 10
    const val LOCATION = 11
    const val MY_INFO = 12
    const val SETTING = 13
    const val PROFILE = 14
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
    const val MASTER_ID = "masterId"
    const val MEMBER = "member"
    const val IS_MEMBER = "isMember"

    /** 정모 관련 **/
    const val ADDRESS = "address"
    const val DATE = "date"
    const val PAY = "pay"
    const val LAT = "lat"
    const val LNG = "lng"
    const val JOIN_MEMBER = "joinMember"
    const val TIME = "time"
    const val LatLng = "latLng"

    /** 채팅 관련 **/
    const val OTHER = "other"

    /** 일반 **/
    const val LAST_TIME = "lastTime"
    const val ACTIVE = "active"
    const val FAVOR = "favor"

    /** Response **/
    const val CODE = "code"
    const val RESULT = "result"
}

object IntentParam {
    const val ACTIVITY = "ACTIVITY"
    const val INTEREST = "INTEREST"
    const val INTEREST_ICON = "INTEREST_ICON"
    const val LOCATION = "LOCATION"
    const val ADDRESS = "ADDRESS"
    const val TYPE = "TYPE"
    const val USER = "USER"
    const val MOIM = "MOIM"
}


object GroupListType {
    const val FAVORITE = 1
    const val RECENT = 2
}
