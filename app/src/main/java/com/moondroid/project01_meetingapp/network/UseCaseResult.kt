package com.moondroid.project01_meetingapp.network

import com.moondroid.project01_meetingapp.model.BaseResponse

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: BaseResponse) : UseCaseResult<T>()
    class Fail(val errCode: Int) : UseCaseResult<Nothing>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}