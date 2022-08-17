package com.moondroid.project01_meetingapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("birth")
    val birth: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("interest")
    val interest: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("message")
    val message: String
)