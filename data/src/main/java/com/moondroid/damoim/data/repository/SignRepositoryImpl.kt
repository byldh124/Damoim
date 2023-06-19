package com.moondroid.damoim.data.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.common.Constants
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiException
import com.moondroid.damoim.domain.model.status.ApiStatus
import com.moondroid.damoim.domain.repository.SignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SignRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) :
    SignRepository {
    override suspend fun signUp(body: JsonObject): Flow<ApiStatus<Profile>> {
        return flow<ApiStatus<Profile>> {
            remoteDataSource.signUp(body).run {
                when (this) {
                    is ApiStatus.Success -> {
                        localDataSource.deleteProfileAll()
                        localDataSource.insertProfile(response)
                        emit(ApiStatus.Success(response.toProfile()))
                    }

                    is ApiStatus.Error -> {
                        emit(ApiStatus.Error(throwable))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signIn(body: JsonObject): Flow<ApiStatus<Profile>> {
        return flow<ApiStatus<Profile>> {
            remoteDataSource.signIn(body).run {
                when (this) {
                    is ApiStatus.Success -> {
                        localDataSource.deleteProfileAll()
                        localDataSource.insertProfile(response)
                        emit(ApiStatus.Success(response.toProfile()))
                    }

                    is ApiStatus.Error -> {
                        emit(ApiStatus.Error(throwable))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun signInSocial(body: JsonObject): Flow<ApiStatus<Profile>> {
        return flow<ApiStatus<Profile>> {
            remoteDataSource.signInSocial(body).run {
                when (this) {
                    is ApiStatus.Success -> {
                        localDataSource.deleteProfileAll()
                        localDataSource.insertProfile(response)
                        emit(ApiStatus.Success(response.toProfile()))
                    }

                    is ApiStatus.Error -> {
                        emit(ApiStatus.Error(throwable))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSalt(id: String): Flow<ApiStatus<String>> {
        return flow<ApiStatus<String>> {
            remoteDataSource.getSalt(id).run {
                when (this) {
                    is ApiStatus.Success -> {
                        if (response.code == ResponseCode.SUCCESS) {
                            emit(ApiStatus.Success(response.salt!!))
                        } else {
                            emit(ApiStatus.Error(ApiException(response.code, response.message)))
                        }
                    }

                    is ApiStatus.Error -> {
                        emit(ApiStatus.Error(throwable))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}