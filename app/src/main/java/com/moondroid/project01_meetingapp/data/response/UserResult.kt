package com.moondroid.project01_meetingapp.data.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moondroid.project01_meetingapp.utils.RequestParam

/**
 * User Model class for Room
 * */
@Keep
data class UserResult(
    @SerializedName(RequestParam.ID)
    val id: String,
    @SerializedName(RequestParam.NAME)
    val name: String,
    @SerializedName(RequestParam.BIRTH)
    val birth: String,
    @SerializedName(RequestParam.GENDER)
    val gender: String,
    @SerializedName(RequestParam.LOCATION)
    val location: String,
    @SerializedName(RequestParam.INTEREST)
    val interest: String,
    @SerializedName(RequestParam.THUMB)
    val thumb: String,
    @SerializedName(RequestParam.MESSAGE)
    val message: String
)