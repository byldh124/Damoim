package com.moondroid.damoim.model

data class Group(
    val groupInfo: GroupInfo,
    val groupMember: GroupMember,
    val groupSubInfo: GroupSubInfo
)

data class GroupInfo(
    val meetName: String,
    val meetLocation: String,
    val purposeMessage: String,
    val titleImgUrl: String,
    val meetInterest: String,
    val introImgUrl: String,
    val message: String,
    val masterId: String
)

data class GroupMember(
    val master:String,
    val member: ArrayList<String>
)

data class GroupSubInfo(
    val introImgUrl: String,
    val message: String
)