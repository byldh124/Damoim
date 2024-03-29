package com.moondroid.damoim.data.api.response

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResponseAdapter<T>(
    private val successType: Type,
) : CallAdapter<T, Call<ApiStatus<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiStatus<T>> = ResponseCall(call)
}