package com.moondroid.project01_meetingapp.model

import com.moondroid.project01_meetingapp.utils.DMUtils

data class Group(
    val groupInfo: GroupInfo,
    val groupMember: GroupMember,
    val groupSubInfo: GroupSubInfo
)

data class GroupInfo(
    val title: String,
    val location: String,
    val purpose: String,
    val interest: String,
    val thumb: String,
    val image: String,
    val information: String,
    val masterId: String
)

data class GroupMember(
    val master: String,
    val member: ArrayList<String>
)

data class GroupSubInfo(
    val introImgUrl: String,
    val message: String
)

data class Moim(
    val title: String,
    val address: String,
    val date: String,
    val time: String,
    val pay: String,
    val lat: Double,
    val lng: Double,
    val joinMember: String
)