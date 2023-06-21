package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiResult<Profile>>

    suspend fun updateToken(token: String): Flow<ApiResult<BaseResponse>>

    suspend fun updateInterest(interest: String): Flow<ApiResult<BaseResponse>>
}