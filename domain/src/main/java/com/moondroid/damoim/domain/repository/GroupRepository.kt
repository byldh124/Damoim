package com.moondroid.damoim.domain.repository

import com.moondroid.damoim.common.Constants
import com.moondroid.damoim.domain.model.BaseResponse
import com.moondroid.damoim.domain.model.status.ApiStatus
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroup(): Flow<ApiStatus<BaseResponse>>
    suspend fun getGroup(type: GroupType): Flow<ApiStatus<BaseResponse>>
    suspend fun getMoim() : Flow<ApiStatus<BaseResponse>>
}