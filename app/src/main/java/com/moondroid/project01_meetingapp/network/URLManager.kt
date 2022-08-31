package com.moondroid.project01_meetingapp.network

object URLManager {

    //const val ENV = "TEST"
    const val ENV = "PROD"

    const val BASE_URL_TEST = "http://moondroid.dothome.co.kr/damoim-test/"
    const val BASE_URL_PROD = "http://moondroid.dothome.co.kr/damoim/"

    const val BASE_URL = "http://moondroid.dothome.co.kr/damoim-test/"

    /** 앱 기능 관련 **/
    const val ChECK_APP_VERSION = "app/checkVersion.php"

    /** 그룹 정보 관련 **/
    const val GET_GROUP = "group/group.php"
    const val GET_MEMBER = "group/member.php"
    const val GET_MY_GROUP = "group/myGroup.php"
    const val GET_FAVORITE = "group/favorite.php"
    const val GET_RECENT = "group/recent.php"
    const val GET_MOIM = "group/moim.php"

    /** 유저-그룹 관련 **/
    const val SAVE_RECENT = "user/updateRecent.php"
    const val SAVE_FAVOR = "user/updateFavor.php"
    const val JOIN = "user/join.php"
    const val GET_FAVOR = "user/favor.php"

    /** 그룹 생성 수정 **/
    const val CREATE_GROUP = "group/create.php"
    const val UPDATE_GROUP = "group/update.php"

    /** 회원가입 로그인 관련 **/
    const val SIGN_IN = "sign/signIn.php"
    const val SIGN_UP = "sign/signUp.php"
    const val SIGN_IN_KAKAO = "sign/signInKakao.php"
    const val SALT = "sign/salt.php"


    /** 유저 정보 관련 **/
    const val UPDATE_TOKEN = "user/updateToken.php"
    const val UPDATE_PROFILE = "user/updateProfile.php"
    const val UPDATE_INTEREST = "user/updateInterest.php"


}