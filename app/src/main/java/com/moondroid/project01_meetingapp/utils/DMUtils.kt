package com.moondroid.project01_meetingapp.utils

import com.moondroid.project01_meetingapp.base.BaseActivity
import java.security.MessageDigest

internal object DMUtils {
    fun getImgUrl(subUrl: String): String {
        return if (subUrl.startsWith("http")) {
            subUrl
        } else {
            "http://moondroid.dothome.co.kr/damoim/$subUrl"
        }
    }

    fun hashingPw(password: String, salt: String): String{
        val md: MessageDigest = MessageDigest.getInstance("SHA-256") // SHA-256 해시함수를 사용
        var output: ByteArray = password.toByteArray()

        // key-stretching
        for (i in 0..999) {
            val temp: String = byteToString(output) + salt // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.toByteArray(Charsets.UTF_16)) // temp 의 문자열을 해싱하여 md 에 저장해둔다
            output = md.digest() // md 객체의 다이제스트를 얻어 password 를 갱신한다
        }

        return byteToString(output)
    }

    fun byteToString(byteArray: ByteArray) : String{
        val sb = StringBuilder()
        for (bt in byteArray) {
            sb.append(String.format("%02x", bt))
        }

        return sb.toString()
    }

    fun exitApplication(activity: BaseActivity){
        activity.moveTaskToBack(true)
        activity.finish()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}