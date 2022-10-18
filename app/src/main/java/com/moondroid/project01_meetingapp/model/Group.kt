package com.moondroid.project01_meetingapp.model

import com.google.gson.annotations.SerializedName
import com.moondroid.project01_meetingapp.utils.RequestParam


data class GroupInfo(
    @SerializedName(RequestParam.TITLE)
    val title: String,
    @SerializedName(RequestParam.LOCATION)
    val location: String,
    @SerializedName(RequestParam.PURPOSE)
    val purpose: String,
    @SerializedName(RequestParam.INTEREST)
    val interest: String,
    @SerializedName(RequestParam.THUMB)
    val thumb: String,
    @SerializedName(RequestParam.IMAGE)
    val image: String,
    @SerializedName(RequestParam.INFORMATION)
    val information: String,
    @SerializedName(RequestParam.MASTER_ID)
    val masterId: String,
    @SerializedName(RequestParam.MEMBER)
    var member: List<DMUser> = ArrayList(),
    @SerializedName(RequestParam.IS_MEMBER)
    var isMember:Boolean = false
)

//constructor initialize for FirebaseDataBase
data class Chat(
    @SerializedName(RequestParam.ID)
    val id: String = "",
    @SerializedName(RequestParam.NAME)
    var name:String = "",
    @SerializedName(RequestParam.TIME)
    val time: String = "",
    @SerializedName(RequestParam.THUMB)
    var thumb: String = "",
    @SerializedName(RequestParam.MESSAGE)
    val message: String = "",
    @SerializedName(RequestParam.OTHER)
    var other: Boolean = true
)
