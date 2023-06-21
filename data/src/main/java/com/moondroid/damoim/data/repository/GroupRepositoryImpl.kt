package com.moondroid.damoim.data.repository

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.data.source.remote.RemoteDataSource
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.data.api.response.ApiStatus
import com.moondroid.damoim.data.mapper.DataMapper.toGroupItem
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) : GroupRepository {
    override suspend fun getGroup(): Flow<ApiResult<List<GroupItem>>> = flow<ApiResult<List<GroupItem>>> {
        remoteDataSource.getGroupList().run {
            when (this) {
                is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toGroupItem() }))
                is ApiResult.Fail -> emit(ApiResult.Fail(code))
                is ApiResult.Error -> emit(ApiResult.Error(throwable))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getGroup(type: GroupType): Flow<ApiResult<List<GroupItem>>> = flow<ApiResult<List<GroupItem>>> {
        localDataSource.getProfile().run {
            this?.let { profile ->
                remoteDataSource.getGroupList(profile.id, type).run {
                    when (this) {
                        is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toGroupItem() }))
                        is ApiResult.Fail -> emit(ApiResult.Fail(code))
                        is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    }
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMoim(): Flow<ApiResult<List<MoimItem>>> {
        TODO("Not yet implemented")
    }

    override suspend fun createMoim(): Flow<ApiResult<MoimItem>> {
        TODO("Not yet implemented")
    }

}