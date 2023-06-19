package com.moondroid.damoim.data.model.dto

import com.moondroid.damoim.domain.model.Profile

data class GroupItemDTO(
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