package com.moondroid.project01_meetingapp.domain.repository

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): Flow<DMResult<BaseResponse>>  // 앱 버전 정보 체크
}