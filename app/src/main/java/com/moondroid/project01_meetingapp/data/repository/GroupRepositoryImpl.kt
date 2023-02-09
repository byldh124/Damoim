package com.moondroid.project01_meetingapp.data.repository

import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.common.DataSourceException
import com.moondroid.project01_meetingapp.data.datasource.local.LocalDataSource
import com.moondroid.project01_meetingapp.data.datasource.remote.RemoteDataSource
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.repository.AppRepository
import com.moondroid.project01_meetingapp.domain.repository.GroupRepository
import com.moondroid.project01_meetingapp.utils.GroupType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GroupRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : GroupRepository {
    override suspend fun getGroup(): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.getGroup().run {
                when (this) {
                    is DMResult.Success -> {
                        emit(DMResult.Success(response))
                    }

                    is DMResult.Error -> {
                        emit(DMResult.Error(exception))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getGroup(type: GroupType): Flow<DMResult<BaseResponse>> {
        return flow {
            localDataSource.getUser().run {
                if (this != null) {
                    remoteDataSource.getGroup(this.id, type).run {
                        when (this) {
                            is DMResult.Success -> {
                                emit(DMResult.Success(response))
                            }
                            is DMResult.Error -> {
                                emit(DMResult.Error(exception))
                            }
                        }
                    }
                } else {
                    emit(DMResult.Error(DataSourceException.Unexpected(R.string.error_client_unexpected_message)))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMoim(): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.getMoim().run {
                when (this) {
                    is DMResult.Success -> {
                        emit(DMResult.Success(response))
                    }

                    is DMResult.Error -> {
                        emit(DMResult.Error(exception))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}