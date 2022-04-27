package com.moondroid.damoim.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("result")
    val body:JsonElement,
    @SerializedName("message")
    val msg:String,
    @SerializedName("code")
    val code:Int
)

data class MsgResponse(
    @SerializedName("message")
    val msg:String,
    @SerializedName("code")
    val code:Int
)

data class BoolResponse(
    @SerializedName("result")
    val body: Boolean,
    @SerializedName("message")
    val msg:String,
    @SerializedName("code")
    val code:Int
)