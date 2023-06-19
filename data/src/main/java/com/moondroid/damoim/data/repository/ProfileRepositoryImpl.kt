package com.moondroid.damoim.data.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toProfile
import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.ApiStatus
import com.moondroid.damoim.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiStatus<Profile>> {
        return flow<ApiStatus<Profile>> {
            localDataSource.getProfile().run {
                if (!isNullOrEmpty()) {
                    emit(ApiStatus.Success(last().toProfile()))
                } else {
                    emit(ApiStatus.Error(IllegalStateException("Data is empty")))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateToken(body: JsonObject): Flow<ApiStatus<BaseResponse>> {
        return flow<ApiStatus<BaseResponse>> {
            remoteDataSource.updateToken(body).run {
                when (this) {
                    is ApiStatus.Success -> {
                        emit(ApiStatus.Success(response))
                    }

                    is ApiStatus.Error -> {
                        emit(ApiStatus.Error(exception))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateInterest(interest: String): Flow<ApiStatus<BaseResponse>> {
        return flow<ApiStatus<BaseResponse>> {
            getProfile().collect { result ->
                result.onSuccess {
                    remoteDataSource.updateInterest(it.id, interest).run {
                        when (this) {
                            is ApiStatus.Success -> {
                                emit(ApiStatus.Success(response))
                            }

                            is ApiStatus.Error -> {
                                emit(ApiStatus.Error(exception))
                            }
                        }
                    }
                }.onError {
                    emit(ApiStatus.Error(it))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun saveProfile(user: User) {
        localDataSource.insertUser(user)
    }
}