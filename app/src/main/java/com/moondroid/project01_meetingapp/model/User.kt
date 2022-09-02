package com.moondroid.project01_meetingapp.model

import com.google.gson.annotations.SerializedName
import com.moondroid.project01_meetingapp.utils.RequestParam

data class User(
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