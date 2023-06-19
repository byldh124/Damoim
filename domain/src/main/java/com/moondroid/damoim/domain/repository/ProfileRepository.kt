package com.moondroid.damoim.domain.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiStatus
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiStatus<Profile>>

    suspend fun updateToken(body: JsonObject): Flow<ApiStatus<BaseResponse>>

    suspend fun updateInterest(interest: String) : Flow<ApiStatus<BaseResponse>>

    fun saveProfile(Profile: Profile)
}