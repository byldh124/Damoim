package com.moondroid.damoim.data.repository

import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.mapper.DataMapper.toBaseResponse
import com.moondroid.damoim.domain.model.status.ApiStatus
import com.moondroid.damoim.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : AppRepository {
    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): Flow<ApiStatus<BaseResponse>> {
        return flow<ApiStatus<BaseResponse>> {
            remoteDataSource.checkAppVersion(packageName, versionCode, versionName).run {
                when (this) {
                    is ApiStatus.Success -> emit(ApiStatus.Success(response.toBaseResponse()))
                    is ApiStatus.Error -> emit(ApiStatus.Error(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}