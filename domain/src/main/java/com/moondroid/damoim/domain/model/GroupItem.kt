package com.moondroid.damoim.domain.model

data class GroupItem(
    val title: String,
    val location: String,
    val purpose: String,
    val interest: String,
    val thumb: String,
    val image: String,
    val information: String,
    val masterId: String,
    var member: List<Profile> = ArrayList(),
    var isMember: Boolean = false
)