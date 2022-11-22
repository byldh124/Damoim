package com.moondroid.project01_meetingapp.domain.repository

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface SignRepository {
    suspend fun signUp(body: JsonObject): Flow<DMResult<BaseResponse>>           // 회원가입
    suspend fun signIn(body: JsonObject): Flow<DMResult<BaseResponse>>           // 로그인
    suspend fun getSalt(id: String): Flow<DMResult<BaseResponse>>                // 로그인 관련
    suspend fun signInSocial(body: JsonObject): Flow<DMResult<BaseResponse>>      // 카카오 로그인
}