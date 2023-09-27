package com.moondroid.damoim.domain.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface GroupRepository {
    suspend fun getGroupList(type: GroupType): Flow<ApiResult<List<GroupItem>>>
    suspend fun getMoim(): Flow<ApiResult<List<MoimItem>>>

    suspend fun createGroup(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ): Flow<ApiResult<GroupItem>>

    suspend fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        image: File?
    ): Flow<ApiResult<GroupItem>>

    suspend fun createMoim(body: JsonObject): Flow<ApiResult<MoimItem>>
}