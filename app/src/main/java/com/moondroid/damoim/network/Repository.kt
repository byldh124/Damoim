package com.moondroid.damoim.network

import com.moondroid.damoim.model.BaseResponse
import com.moondroid.damoim.utils.firebase.DMCrash
import java.lang.Exception

interface Repository {
    /* 인증 */
    suspend fun loadGroup(): UseCaseResult<BaseResponse>
    suspend fun loadMember(meetName: String): UseCaseResult<BaseResponse>
    suspend fun signIn(userId: String): UseCaseResult<BaseResponse>
    suspend fun signInWithKakao(
        id: String,
        name: String,
        profileThumb: String
    ): UseCaseResult<BaseResponse>

}

class RepositoryImpl(private val api: ApiInterface) : Repository {

    override suspend fun loadGroup(): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getGroup().await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.getInstance()
                .log(e.toString())
            UseCaseResult.Error(e)
        }
    }

    override suspend fun loadMember(meetName: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getMember(meetName).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.getInstance()
                .log(e.toString())
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signIn(userId: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.signIn(userId).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.getInstance()
                .log(e.toString())
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signInWithKakao(
        id: String,
        name: String,
        profileThumb: String
    ): UseCaseResult<BaseResponse> {
        return try {
            val result = api.signInWithKakao(id, name, profileThumb).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.getInstance()
                .log(e.toString())
            UseCaseResult.Error(e)
        }
    }


}
