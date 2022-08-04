package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import kotlin.Exception

interface Repository {
    /* 인증 */
    suspend fun loadGroup(): UseCaseResult<BaseResponse>
    suspend fun loadMyGroup(userId: String): UseCaseResult<BaseResponse>
    suspend fun loadMember(meetName: String): UseCaseResult<BaseResponse>
    suspend fun signIn(body: JsonObject): UseCaseResult<BaseResponse>
    suspend fun getSalt(id: String): UseCaseResult<BaseResponse>
    suspend fun signInKakao(body: JsonObject): UseCaseResult<BaseResponse>

    suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): UseCaseResult<BaseResponse>

    suspend fun signUp(body: JsonObject): UseCaseResult<BaseResponse>

    suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse>
}

class RepositoryImpl(private val api: ApiInterface) : Repository {

    override suspend fun loadGroup(): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getGroup().await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun loadMyGroup(userId: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getMyGroup(userId).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun loadMember(meetName: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getMember(meetName).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signIn(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            val result = api.signIn(body).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getSalt(id: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getSalt(id).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signInKakao(body: JsonObject): UseCaseResult<BaseResponse> {
        return  try{
            val result = api.signInkakao(body).await()
            UseCaseResult.Success(result)
        } catch (e: Exception){
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }


    override suspend fun signUp(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            val result = api.signUp(body).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            val result = api.updateToken(body).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): UseCaseResult<BaseResponse> {
        return try {
            val result = api.checkAppVersion(packageName, versionCode, versionName).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }


}
