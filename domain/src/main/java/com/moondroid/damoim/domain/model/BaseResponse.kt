package com.moondroid.damoim.domain.model

data class BaseResponse(
    val code: Int,
    val message: String? = null
)