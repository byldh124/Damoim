package com.moondroid.project01_meetingapp.data.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.datasource.local.LocalDataSource
import com.moondroid.project01_meetingapp.data.datasource.remote.RemoteDataSource
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.repository.SignRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SignRepositoryImpl(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) : SignRepository {
    override suspend fun signUp(body: JsonObject): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.signUp(body).run {
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

    override suspend fun signIn(body: JsonObject): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.signIn(body).run {
                when (this) {
                    is DMResult.Success -> {
                        emit(DMResult.Success(response))
                        val user = Gson().fromJson(response.body, User::class.java)
                        localDataSource.insertUser(user)
                    }

                    is DMResult.Error -> {
                        emit(DMResult.Error(exception))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSalt(id: String): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.getSalt(id).run {
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

    override suspend fun signInSocial(body: JsonObject): Flow<DMResult<BaseResponse>> {
        return flow {
            remoteDataSource.signInSocial(body).run {
                when (this) {
                    is DMResult.Success -> {
                        emit(DMResult.Success(response))
                        val user = Gson().fromJson(response.body, User::class.java)
                        localDataSource.insertUser(user)
                    }

                    is DMResult.Error -> {
                        emit(DMResult.Error(exception))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}