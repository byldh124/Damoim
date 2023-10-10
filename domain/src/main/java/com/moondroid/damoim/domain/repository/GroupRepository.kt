package com.moondroid.damoim.domain.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.domain.model.Profile
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

    suspend fun createMoim(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ): Flow<ApiResult<Unit>>

    suspend fun getMembers(title: String): Flow<ApiResult<List<Profile>>>
    suspend fun getMoims(title: String): Flow<ApiResult<List<MoimItem>>>
    suspend fun saveRecent(id: String, title: String, lastTime: String): Flow<ApiResult<Unit>>
    suspend fun join(id: String, title: String): Flow<ApiResult<Unit>>
    suspend fun getFavor(id: String, title: String) : Flow<ApiResult<Boolean>>
    suspend fun setFavor(id: String, title: String, active: Boolean) : Flow<ApiResult<Unit>>
}