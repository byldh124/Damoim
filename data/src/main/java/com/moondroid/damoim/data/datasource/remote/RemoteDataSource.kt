package com.moondroid.damoim.data.datasource.remote

import com.google.gson.JsonObject
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.response.SaltResponse
import com.moondroid.damoim.domain.model.status.ApiStatus

interface RemoteDataSource {
    suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): ApiStatus<BaseResponseDTO>

    suspend fun signUp(body: JsonObject): ApiStatus<ProfileEntity>              // 회원가입
    suspend fun signIn(body: JsonObject): ApiStatus<ProfileEntity>              // 로그인
    suspend fun getSalt(id: String): ApiStatus<SaltResponse>                          // 로그인 관련
    suspend fun signInSocial(body: JsonObject): ApiStatus<ProfileEntity>        // 카카오 로그인
    suspend fun updateToken(body: JsonObject): ApiStatus<BaseResponseDTO>       // 푸시토큰 업데이트
    suspend fun getGroup(): ApiStatus<BaseResponseDTO>
    suspend fun getGroup(id: String, type: GroupType): ApiStatus<BaseResponseDTO>
    suspend fun getMoim(): ApiStatus<BaseResponseDTO>
    suspend fun updateInterest(id: String, interest: String): ApiStatus<BaseResponseDTO>
}