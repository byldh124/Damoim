package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface SignRepository {
    suspend fun signUp(
        id: String,
        hashPw: String,
        salt: String,
        name: String,
        birth: String,
        gender: String,
        location: String,
        interest: String,
        thumb: String
    ): Flow<ApiResult<Profile>>           // 회원가입

    suspend fun signIn(id: String, hashPw: String): Flow<ApiResult<Profile>>            // 로그인
    suspend fun getSalt(id: String): Flow<ApiResult<String>>                            // 로그인 관련
    suspend fun socialSign(id: String): Flow<ApiResult<Profile>>                      // 카카오 로그인
}