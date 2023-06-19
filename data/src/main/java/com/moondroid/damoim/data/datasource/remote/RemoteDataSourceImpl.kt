package com.moondroid.damoim.data.datasource.remote

import com.google.gson.JsonObject
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.data.api.ApiInterface
import com.moondroid.damoim.data.mapper.DataMapper.toProfileEntity
import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.response.SaltResponse
import com.moondroid.damoim.domain.model.status.ApiException
import com.moondroid.damoim.domain.model.status.ApiStatus

class RemoteDataSourceImpl(private val api: ApiInterface) : RemoteDataSource {
    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): ApiStatus<BaseResponseDTO> = api.checkAppVersion(packageName, versionCode, versionName)

    override suspend fun signUp(body: JsonObject): ApiStatus<ProfileEntity> {
        return when (val result = api.signUp(body)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiStatus.Success(result.response.profileDTO!!.toProfileEntity())
                } else {
                    ApiStatus.Error(ApiException(result.response.code, result.response.message))
                }
            }

            is ApiStatus.Error -> ApiStatus.Error(result.throwable)
        }
    }

    override suspend fun signIn(body: JsonObject): ApiStatus<ProfileEntity> {
        return when (val result = api.signIn(body)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiStatus.Success(result.response.profileDTO!!.toProfileEntity())
                } else {
                    ApiStatus.Error(ApiException(result.response.code, result.response.message))
                }
            }

            is ApiStatus.Error -> ApiStatus.Error(result.throwable)
        }
    }

    override suspend fun getSalt(id: String): ApiStatus<SaltResponse> = api.getSalt(id)


    override suspend fun signInSocial(body: JsonObject): ApiStatus<ProfileEntity> {
        return when (val result = api.signIn(body)) {
            is ApiStatus.Success -> {
                if (result.response.code == ResponseCode.SUCCESS) {
                    ApiStatus.Success(result.response.profileDTO!!.toProfileEntity())
                } else {
                    ApiStatus.Error(ApiException(result.response.code, result.response.message))
                }
            }

            is ApiStatus.Error -> ApiStatus.Error(result.throwable)
        }
    }

    override suspend fun updateToken(body: JsonObject): ApiStatus<BaseResponseDTO> = api.updateToken(body)

    override suspend fun updateInterest(id: String, interest: String): ApiStatus<BaseResponseDTO> =
        api.updateInterest(id, interest)


    override suspend fun getGroup(): ApiStatus<BaseResponseDTO> {
        return try {
            val result = api.getGroup()
            if (result.isSuccessful) {
                result.body()?.let {
                    ApiStatus.Success(it)
                } ?: run {
                    ApiStatus.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                ApiStatus.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            ApiStatus.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun getGroup(id: String, type: GroupType): ApiStatus<BaseResponseDTO> {
        return try {
            val result = when (type) {
                GroupType.FAVORITE -> api.getFavorite(id)
                GroupType.RECENT -> api.getRecent(id)
                GroupType.MY_GROUP -> api.getMyGroup(id)
            }
            if (result.isSuccessful) {
                result.body()?.let {
                    ApiStatus.Success(it)
                } ?: run {
                    ApiStatus.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                ApiStatus.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            ApiStatus.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun getMoim(): ApiStatus<BaseResponseDTO> {
        return try {
            val result = api.getMoim()
            if (result.isSuccessful) {
                result.body()?.let {
                    ApiStatus.Success(it)
                } ?: run {
                    ApiStatus.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                ApiStatus.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            ApiStatus.Error(RequestErrorHandler.getRequestError(e))
        }
    }
}