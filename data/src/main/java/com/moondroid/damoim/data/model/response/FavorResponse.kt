package com.moondroid.damoim.data.model.response

import com.google.gson.annotations.SerializedName

data class FavorResponse(
    val code: Int,
    val message: String,
    @SerializedName("favor")
    val result : Boolean
)
