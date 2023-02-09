package com.moondroid.project01_meetingapp.domain.repository

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(): Flow<DMResult<User>>

    suspend fun updateToken(body: JsonObject): Flow<DMResult<BaseResponse>>

    suspend fun updateInterest(interest: String) : Flow<DMResult<BaseResponse>>

    fun insertUser(user: User)
}