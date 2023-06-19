package com.moondroid.project01_meetingapp.data.repository

import com.moondroid.project01_meetingapp.data.common.ApiStatus
import com.moondroid.project01_meetingapp.data.common.DataSourceException
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.response.BaseResponse
import com.moondroid.damoim.domain.repository.GroupRepository
import com.moondroid.project01_meetingapp.utils.GroupType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GroupRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : GroupRepository {
    override suspend fun getGroup(): Flow<ApiStatus<BaseResponse>> {
        return flow {
            remoteDataSource.getGroup().run {
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

    override suspend fun getGroup(type: GroupType): Flow<ApiStatus<BaseResponse>> {
        return flow {
            localDataSource.loadProfile().run {
                if (this != null) {
                    remoteDataSource.getGroup(this.id, type).run {
                        when (this) {
                            is ApiStatus.Success -> {
                                emit(ApiStatus.Success(response))
                            }
                            is ApiStatus.Error -> {
                                emit(ApiStatus.Error(exception))
                            }
                        }
                    }
                } else {
                    emit(ApiStatus.Error(DataSourceException.Unexpected(R.string.error_client_unexpected_message)))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMoim(): Flow<ApiStatus<BaseResponse>> {
        return flow {
            remoteDataSource.getMoim().run {
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
}