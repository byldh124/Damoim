package com.moondroid.project01_meetingapp.data.repository

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.common.DataSourceException
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.data.datasource.local.LocalDataSource
import com.moondroid.project01_meetingapp.data.datasource.remote.RemoteDataSource
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) : UserRepository{
    override suspend fun getUser(): Flow<DMResult<User>> {
        return flow {
            localDataSource.getUser().run {
                if (this != null) {
                    emit(DMResult.Success(this))
                } else {
                    emit(DMResult.Error(DataSourceException.Unexpected(R.string.error_client_unexpected_message)))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateToken(body: JsonObject) : Flow<DMResult<BaseResponse>>{
        return flow {
            remoteDataSource.updateToken(body).run {
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

    override suspend fun updateInterest(interest: String): Flow<DMResult<BaseResponse>> {
        return flow {
            getUser().collect { result ->
                result.onSuccess {
                    remoteDataSource.updateInterest(it.id, interest).run {
                        when (this) {
                            is DMResult.Success -> {
                                emit(DMResult.Success(response))
                            }
                            is DMResult.Error -> {
                                emit(DMResult.Error(exception))
                            }
                        }
                    }
                }.onError {
                    emit(DMResult.Error(it))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun insertUser(user: User) {
        localDataSource.insertUser(user)
    }
}