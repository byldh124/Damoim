package com.moondroid.damoim.data.source.remote

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.response.SaltResponse
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.domain.model.status.ApiResult

interface RemoteDataSource {
    suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): ApiResult<BaseResponseDTO>

    suspend fun signUp(request: SignUpRequest): ApiResult<ProfileEntity>                              // 회원가입
    suspend fun signIn(request: SignInRequest): ApiResult<ProfileEntity>                        // 로그인
    suspend fun getSalt(request: SaltRequest): ApiResult<SaltResponse>                                    // 로그인 관련
    suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileEntity>                        // 카카오 로그인
    suspend fun updateToken(id: String, token: String): ApiResult<BaseResponseDTO>              // 푸시토큰 업데이트
    suspend fun getGroupList(): ApiResult<List<GroupItemDTO>>
    suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>>
    suspend fun getMoimList(): ApiResult<List<MoimItemDTO>>
    suspend fun updateInterest(id: String, interest: String): ApiResult<BaseResponseDTO>
}