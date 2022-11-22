package com.moondroid.project01_meetingapp.data.repository

import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.common.DataSourceException
import com.moondroid.project01_meetingapp.data.datasource.local.LocalDataSource
import com.moondroid.project01_meetingapp.data.datasource.remote.RemoteDataSource
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.data.response.toDomain
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AppRepository {
    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.checkAppVersion(
                packageName, versionCode, versionName
            ).run {
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

    override suspend fun getUser(): Flow<DMResult<User>> {
        return flow {
            localDataSource.getUser().run {
                if (this.isNotEmpty()) {
                    emit(DMResult.Success(localDataSource.getUser().last().toDomain()))
                } else {
                    emit(DMResult.Error(DataSourceException.Unexpected(R.string.error_client_unexpected_message)))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}