package com.moondroid.damoim.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")
    val id: String,
    @SerializedName("userName")
    val name: String,
    @SerializedName("userBirthDate")
    val birth: String,
    @SerializedName("userGender")
    val gender: String,
    @SerializedName("userLocation")
    val location: String,
    @SerializedName("userInterest")
    val interest: String,
    @SerializedName("userProfileImgUrl")
    val thumb: String,
    @SerializedName("userProfileMessage")
    val msg: String,
    @SerializedName("FCMToken")
    val fcmToken: String
)