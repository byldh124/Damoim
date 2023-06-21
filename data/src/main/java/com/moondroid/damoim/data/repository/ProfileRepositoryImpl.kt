package com.moondroid.damoim.data.repository

import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.source.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toBaseResponse
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.data.api.response.ApiStatus
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            localDataSource.getProfile().run {
                this?.let {
                    emit(ApiResult.Success(it.toProfile()))
                } ?: run {
                    emit(ApiResult.Error(IllegalStateException("Data is empty")))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateToken(token: String): Flow<ApiResult<BaseResponse>> {
        return flow<ApiResult<BaseResponse>> {
            localDataSource.getProfile().run {
                this?.let {
                    remoteDataSource.updateToken(it.id, interest).run {
                        when (this) {
                            is ApiResult.Success -> emit(ApiResult.Success(response.toBaseResponse()))
                            is ApiResult.Fail -> emit(ApiResult.Fail(code))
                            is ApiResult.Error -> emit(ApiResult.Error(throwable))
                        }
                    }
                } ?: run {
                    emit(ApiResult.Error(IllegalStateException("Data is empty")))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateInterest(interest: String): Flow<ApiResult<BaseResponse>> {
        return flow<ApiResult<BaseResponse>> {
            localDataSource.getProfile().run {
                this?.let {
                    remoteDataSource.updateInterest(it.id, interest).run {
                        when (this) {
                            is ApiResult.Error -> emit(ApiResult.Error(throwable))
                            is ApiResult.Fail -> emit(ApiResult.Fail(code))
                            is ApiResult.Success -> emit(ApiResult.Success(response.toBaseResponse()))
                        }
                    }
                } ?: run {
                    emit(ApiResult.Error(IllegalStateException("Data is empty")))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}