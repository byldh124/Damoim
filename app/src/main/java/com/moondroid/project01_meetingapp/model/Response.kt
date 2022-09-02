package com.moondroid.project01_meetingapp.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.moondroid.project01_meetingapp.utils.RequestParam

data class BaseResponse(
    @SerializedName(RequestParam.RESULT)
    val body: JsonElement,
    @SerializedName(RequestParam.MESSAGE)
    val msg: String,
    @SerializedName(RequestParam.CODE)
    val code: Int
)

data class BoolResponse(
    @SerializedName(RequestParam.RESULT)
    val body: Boolean,
    @SerializedName(RequestParam.MESSAGE)
    val msg: String,
    @SerializedName(RequestParam.CODE)
    val code: Int
)