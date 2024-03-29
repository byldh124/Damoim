package com.moondroid.damoim.domain.model.status

sealed class ApiResult<out T> {
    //ResponseCode = 1000(o)
    data class Success<out T>(val response: T) : ApiResult<T>()

    //ResponseCode = 1000(x)
    data class Fail<T>(val code: Int) : ApiResult<T>()

    //통신 에러
    data class Error<T>(val throwable: Throwable) : ApiResult<T>()
}

inline fun <T : Any> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(response)
    return this
}

inline fun <T : Any> ApiResult<T>.onFail(action: (Int) -> Unit): ApiResult<T> {
    if (this is ApiResult.Fail) action(code)
    return this
}

inline fun <T : Any> ApiResult<T>.onError(action: (Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(throwable)
    return this
}