package com.moondroid.project01_meetingapp.domain.repository

import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.model.GroupInfo
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.utils.GroupType
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroup(): Flow<DMResult<BaseResponse>>
    suspend fun getGroup(type: GroupType): Flow<DMResult<BaseResponse>>
    suspend fun getMoim() : Flow<DMResult<BaseResponse>>
}