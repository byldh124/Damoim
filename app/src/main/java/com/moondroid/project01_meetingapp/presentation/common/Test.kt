package com.moondroid.project01_meetingapp.presentation.common

import android.util.Log
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun main() {
    val data = "http://moondroid.dothome.co.kr/damoim/group/member.php?title=%EB%A5%B4%EC%84%B8%EB%9D%BC%ED%95%8C%20%EC%97%B0%EA%B5%AC%EC%86%8C"
    print(
            URLDecoder.decode(data, "utf-8")
    )
}