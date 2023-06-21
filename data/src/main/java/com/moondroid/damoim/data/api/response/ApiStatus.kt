package com.moondroid.damoim.data.api.response

sealed class ApiStatus<out T> {
    data class Success<out T>(val response: T) : ApiStatus<T>()
    data class Error<T>(val throwable: Throwable) : ApiStatus<T>()
}

inline fun <T : Any> ApiStatus<T>.onSuccess(action: (T) -> Unit): ApiStatus<T> {
    if (this is ApiStatus.Success) action(response)
    return this
}

inline fun <T : Any> ApiStatus<T>.onError(action: (Throwable) -> Unit): ApiStatus<T> {
    if (this is ApiStatus.Error) action(throwable)
    return this
}

class ApiException(val code: Int) : RuntimeException()