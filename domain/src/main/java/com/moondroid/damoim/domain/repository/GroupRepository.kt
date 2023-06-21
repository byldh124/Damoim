package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroup(): Flow<ApiResult<List<GroupItem>>>
    suspend fun getGroup(type: GroupType): Flow<ApiResult<List<GroupItem>>>
    suspend fun getMoim() : Flow<ApiResult<List<MoimItem>>>
    suspend fun createMoim() : Flow<ApiResult<MoimItem>>
}