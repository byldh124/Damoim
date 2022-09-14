package com.moondroid.project01_meetingapp.model

import com.google.gson.annotations.SerializedName
import com.moondroid.project01_meetingapp.utils.RequestParam
import com.naver.maps.geometry.LatLng

data class Moim(
    @SerializedName(RequestParam.TITLE)
    val title: String,
    @SerializedName(RequestParam.ADDRESS)
    val address: String,
    @SerializedName(RequestParam.DATE)
    val date: String,
    @SerializedName(RequestParam.TIME)
    val time: String,
    @SerializedName(RequestParam.PAY)
    val pay: String,
    @SerializedName(RequestParam.LAT)
    val lat: Double,
    @SerializedName(RequestParam.LNG)
    val lng: Double,
    @SerializedName(RequestParam.JOIN_MEMBER)
    val joinMember: String
) {
    fun itemPay():String {
        return "참가비 : $pay"
    }
}


data class Address(
    @SerializedName(RequestParam.ADDRESS)
    val address: String,
    @SerializedName(RequestParam.LatLng)
    val latLng: LatLng
)