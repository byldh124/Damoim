package com.moondroid.damoim.domain.model

data class ChatItem(
    val id: String,
    var name:String,
    val time: String,
    var thumb: String,
    val message: String,
    var other: Boolean
)