package com.moondroid.damoim.data.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.source.remote.RemoteDataSource
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.SignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) :
    SignRepository {
    override suspend fun signUp(body: JsonObject): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.signUp(body).run {
                when (this) {
                    is ApiResult.Success -> {
                        localDataSource.deleteProfileAll()
                        localDataSource.insertProfile(response)
                        emit(ApiResult.Success(response.toProfile()))
                    }

                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signIn(body: JsonObject): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.signIn(body).run {
                when (this) {
                    is ApiResult.Success -> {
                        localDataSource.deleteProfileAll()
                        localDataSource.insertProfile(response)
                        emit(ApiResult.Success(response.toProfile()))
                    }

                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signInSocial(body: JsonObject): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.signInSocial(body).run {
                when (this) {
                    is ApiResult.Success -> {
                        localDataSource.deleteProfileAll()
                        localDataSource.insertProfile(response)
                        emit(ApiResult.Success(response.toProfile()))
                    }

                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSalt(id: String): Flow<ApiResult<String>> {
        return flow<ApiResult<String>> {
            remoteDataSource.getSalt(id).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.result))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}