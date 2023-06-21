package com.moondroid.damoim.data.source.remote

import com.google.gson.JsonObject
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.response.SaltResponse
import com.moondroid.damoim.data.api.response.ApiStatus
import com.moondroid.damoim.domain.model.status.ApiResult

interface RemoteDataSource {
    suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): ApiResult<BaseResponseDTO>

    suspend fun signUp(body: JsonObject): ApiResult<ProfileEntity>                  // 회원가입
    suspend fun signIn(body: JsonObject): ApiResult<ProfileEntity>                  // 로그인
    suspend fun getSalt(id: String): ApiResult<SaltResponse>                        // 로그인 관련
    suspend fun signInSocial(body: JsonObject): ApiResult<ProfileEntity>            // 카카오 로그인
    suspend fun updateToken(id: String, token: String): ApiResult<BaseResponseDTO>           // 푸시토큰 업데이트
    suspend fun getGroupList(): ApiResult<List<GroupItemDTO>>
    suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>>
    suspend fun getMoimList(): ApiResult<List<MoimItemDTO>>
    suspend fun updateInterest(id: String, interest: String): ApiResult<BaseResponseDTO>
}