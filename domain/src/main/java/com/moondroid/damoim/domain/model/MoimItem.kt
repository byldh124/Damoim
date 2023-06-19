package com.moondroid.damoim.domain.model

import com.google.gson.annotations.SerializedName

data class MoimItem(
    val title: String,
    val address: String,
    val date: String,
    val time: String,
    val pay: String,
    val lat: Double,
    val lng: Double,
    val joinMember: String
)
