package com.moondroid.damoim.data.source.remote

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.data.api.ApiInterface
import com.moondroid.damoim.data.api.response.ApiStatus
import com.moondroid.damoim.data.mapper.DataMapper.toProfileEntity
import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.dto.GroupItemDTO
import com.moondroid.damoim.data.model.dto.MoimItemDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.request.SaltRequest
import com.moondroid.damoim.data.model.request.SignInRequest
import com.moondroid.damoim.data.model.request.SignUpRequest
import com.moondroid.damoim.data.model.request.SocialSignRequest
import com.moondroid.damoim.data.model.request.UpdateTokenRequest
import com.moondroid.damoim.data.model.response.SaltResponse
import com.moondroid.damoim.domain.model.status.ApiResult
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {

    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): ApiResult<BaseResponseDTO> {
        return when (val result = api.checkAppVersion(packageName, versionCode, versionName)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun signUp(request: SignUpRequest): ApiResult<ProfileEntity> {
        return when (val result = api.signUp(request)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response.profileDTO.toProfileEntity())
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun signIn(request: SignInRequest): ApiResult<ProfileEntity> {
        return when (val result = api.signIn(request)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response.profileDTO.toProfileEntity())
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun getSalt(request: SaltRequest): ApiResult<SaltResponse> {
        return when (val result = api.getSalt(request)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }


    override suspend fun socialSign(request: SocialSignRequest): ApiResult<ProfileEntity> {
        return when (val result = api.signInSocial(request)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response.profileDTO.toProfileEntity())
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun updateToken(id: String, token: String): ApiResult<BaseResponseDTO> {
        return when (val result = api.updateToken(UpdateTokenRequest(id, token))) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }


    override suspend fun updateInterest(id: String, interest: String): ApiResult<BaseResponseDTO> {
        return when (val result = api.updateInterest(id, interest)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun getGroupList(): ApiResult<List<GroupItemDTO>> {
        return when (val result = api.getGroup()) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response.result)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun getGroupList(id: String, type: GroupType): ApiResult<List<GroupItemDTO>> {
        val result = when (type) {
            GroupType.ALL -> api.getGroup()
            GroupType.FAVORITE -> api.getFavorite(id)
            GroupType.RECENT -> api.getRecent(id)
            GroupType.MY_GROUP -> api.getMyGroup(id)
        }
        return when (result) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response.result)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun getMoimList(): ApiResult<List<MoimItemDTO>> {
        return when (val result = api.getMoim()) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiResult.Success(result.response.result)
                } else {
                    ApiResult.Fail(result.response.code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }
}