package com.moondroid.project01_meetingapp.data.datasource.remote

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.utils.GroupType

interface RemoteDataSource {
    suspend fun checkAppVersion(packageName: String,
        versionCode: Int,
        versionName: String): DMResult<BaseResponse>

    suspend fun signUp(body: JsonObject): DMResult<BaseResponse>           // 회원가입
    suspend fun signIn(body: JsonObject): DMResult<BaseResponse>           // 로그인
    suspend fun getSalt(id: String): DMResult<BaseResponse>                // 로그인 관련
    suspend fun signInSocial(body: JsonObject): DMResult<BaseResponse>      // 카카오 로그인
    suspend fun updateToken(body: JsonObject): DMResult<BaseResponse>       // 푸시토큰 업데이트
    suspend fun getGroup(): DMResult<BaseResponse>
    suspend fun getGroup(id: String, type: GroupType) : DMResult<BaseResponse>
    suspend fun getMoim(): DMResult<BaseResponse>
    suspend fun updateInterest(id: String, interest: String): DMResult<BaseResponse>
}