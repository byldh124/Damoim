package com.moondroid.damoim.domain.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface SignRepository {
    suspend fun signUp(body: JsonObject): Flow<ApiResult<Profile>>           // 회원가입
    suspend fun signIn(id: String, hashPw: String): Flow<ApiResult<Profile>>           // 로그인
    suspend fun getSalt(id: String): Flow<ApiResult<String>>                // 로그인 관련
    suspend fun signInSocial(body: JsonObject): Flow<ApiResult<Profile>>      // 카카오 로그인
}