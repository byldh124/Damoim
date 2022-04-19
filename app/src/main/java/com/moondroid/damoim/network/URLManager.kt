package com.moondroid.damoim.network

import com.moondroid.damoim.BuildConfig.BASE_URL
import org.koin.android.BuildConfig

object URLManager {

    //const val ENV = "TEST"
    const val ENV = "PROD"

    const val BASE_URL_TEST = "http://moondroid.dothome.co.kr/damoim-test/"
    const val BASE_URL_PROD = "http://moondroid.dothome.co.kr/damoim/"

    const val BASE_URL = "http://moondroid.dothome.co.kr/damoim-test/"

    const val GET_GROUP = "getItemBaseData.php"
    const val GET_MY_GROUP = "loadUserMeetItem.php"
    const val GET_MEMBER = "loadMembers.php"
    const val SIGN_IN = "loadUserBaseDBOnIntro.php"
    const val SIGN_IN_WITH_KAKAO = "saveUserBaseDataToKakako.php"

}