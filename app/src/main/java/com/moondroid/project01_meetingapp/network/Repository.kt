package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import kotlin.Exception

interface Repository {

    /** 앱 기능 관련 **/
    suspend fun checkAppVersion(                // 앱 버전 정보 체크
        packageName: String,
        versionCode: Int,
        versionName: String
    ): UseCaseResult<BaseResponse>

    /** 그룹 정보 관련 **/
    suspend fun loadGroup(): UseCaseResult<BaseResponse>                        // 전체 그룹
    suspend fun getMyGroup(userId: String): UseCaseResult<BaseResponse>        // 특정 유저가 가입한 그룹
    suspend fun getFavorite(id: String): UseCaseResult<BaseResponse>            // 특정 유저가 관심목록 표시한 그룹
    suspend fun getRecent(id: String): UseCaseResult<BaseResponse>              // 특정 유저가 최근 본 그룹
    suspend fun loadMember(meetName: String): UseCaseResult<BaseResponse>       // 특정 그룹에 가입된 유저


    /** 그룹 생성, 수정 **/
    suspend fun createGroup(                // 그룹 생성
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse>


    /** 회원가입, 로그인 **/
    suspend fun signUp(body: JsonObject): UseCaseResult<BaseResponse>           // 회원가입
    suspend fun signIn(body: JsonObject): UseCaseResult<BaseResponse>           // 로그인
    suspend fun getSalt(id: String): UseCaseResult<BaseResponse>                // 로그인 관련
    suspend fun signInKakao(body: JsonObject): UseCaseResult<BaseResponse>      // 카카오 로그인

    /** 유저 정보 수정 **/
    suspend fun updateProfile(                                                  // 회원 정보 수정
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse>
    suspend fun updateInterest(                                                 // 관심사 변경
        id:String,
        interest: String
    ): UseCaseResult<BaseResponse>
    suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse>      // FCM 토큰 변경
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

    override suspend fun getMyGroup(userId: String): UseCaseResult<BaseResponse> {
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

    override suspend fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return try {
            val result = api.createGroup(body, file).await()
            UseCaseResult.Success(result)
        } catch (e: Exception){
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
        return try {
            val result = api.signInkakao(body).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateProfile(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return try {
            val result = api.updateProfile(body, file).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getFavorite(id: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getFavortite(id).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getRecent(id: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.getRecent(id).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateInterest(id: String, interest: String): UseCaseResult<BaseResponse> {
        return try {
            val result = api.updateInterest(id, interest).await()
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
