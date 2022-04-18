package com.moondroid.damoim.utils

internal object DMUtils {
    fun getImgUrl(subUrl: String): String {
        return if (subUrl.startsWith("http")) {
            subUrl
        } else {
            "http://moondroid.dothome.co.kr/damoim/$subUrl"
        }
    }

}