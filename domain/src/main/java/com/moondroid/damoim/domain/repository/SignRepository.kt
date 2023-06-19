package com.moondroid.damoim.domain.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiStatus
import kotlinx.coroutines.flow.Flow

interface SignRepository {
    suspend fun signUp(body: JsonObject): Flow<ApiStatus<Profile>>           // 회원가입
    suspend fun signIn(body: JsonObject): Flow<ApiStatus<Profile>>           // 로그인
    suspend fun getSalt(id: String): Flow<ApiStatus<String>>                // 로그인 관련
    suspend fun signInSocial(body: JsonObject): Flow<ApiStatus<Profile>>      // 카카오 로그인
}