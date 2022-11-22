package com.moondroid.project01_meetingapp.data.common

sealed class DMResult<out T> {
    data class Success<out T>(val response: T) : DMResult<T>()
    data class Error(val exception: DataSourceException) : DMResult<Nothing>()
}

inline fun <T : Any> DMResult<T>.onSuccess(action: (T) -> Unit): DMResult<T> {
    if (this is DMResult.Success) action(response)
    return this
}

inline fun <T : Any> DMResult<T>.onError(action: (DataSourceException) -> Unit): DMResult<T> {
    if (this is DMResult.Error) action(exception)
    return this
}