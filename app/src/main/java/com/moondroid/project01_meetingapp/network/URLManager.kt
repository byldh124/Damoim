package com.moondroid.project01_meetingapp.network

object URLManager {

    //const val ENV = "TEST"
    const val ENV = "PROD"

    const val BASE_URL_TEST = "http://moondroid.dothome.co.kr/damoim-test/"
    const val BASE_URL_PROD = "http://moondroid.dothome.co.kr/damoim/"

    const val BASE_URL = "http://moondroid.dothome.co.kr/damoim-test/"

    const val GET_GROUP = "group/group.php"
    const val GET_MY_GROUP = "user/myGroup.php"
    const val GET_MEMBER = "group/member.php"
    const val SIGN_IN = "sign/signIn.php"
    const val SIGN_UP = "sign/signUp.php"

    const val SIGN_IN_KAKAO = "sign/signInKakao.php"
    const val SIGN_UP_KAKAO = "sign/signUpKakao.php"

    const val SIGN_IN_WITH_KAKAO = "saveUserBaseDataToKakako.php"
    const val ChECK_APP_VERSION = "app/checkVersion.php"

    const val UPDATE_TOKEN = "user/updateToken.php"

    const val GET_SALT = "user/getSalt.php"

}