package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
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
        id: String,
        interest: String
    ): UseCaseResult<BaseResponse>

    suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse>      // FCM 토큰 변경

    suspend fun getMoim(): UseCaseResult<BaseResponse>
}

class RepositoryImpl @Inject constructor(private val api: ApiInterface) : Repository {

    override suspend fun loadGroup(): UseCaseResult<BaseResponse> {
        return handleResult(api.getGroup())
    }

    override suspend fun getMyGroup(userId: String): UseCaseResult<BaseResponse> {
        return handleResult(api.getMyGroup(userId))
    }

    override suspend fun loadMember(meetName: String): UseCaseResult<BaseResponse> {
        return handleResult(api.getMember(meetName))
    }

    override suspend fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return handleResult(api.createGroup(body, file))
    }

    override suspend fun signIn(body: JsonObject): UseCaseResult<BaseResponse> {
        return handleResult(api.signIn(body))
    }

    override suspend fun getSalt(id: String): UseCaseResult<BaseResponse> {
        return handleResult(api.getSalt(id))
    }

    override suspend fun signInKakao(body: JsonObject): UseCaseResult<BaseResponse> {
        return handleResult(api.signInkakao(body))
    }

    override suspend fun updateProfile(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return handleResult(api.updateProfile(body, file))
    }

    override suspend fun getFavorite(id: String): UseCaseResult<BaseResponse> {
        return handleResult(api.getFavortite(id))
    }

    override suspend fun getRecent(id: String): UseCaseResult<BaseResponse> {
        return handleResult(api.getRecent(id))
    }

    override suspend fun updateInterest(id: String, interest: String): UseCaseResult<BaseResponse> {
        return handleResult(api.updateInterest(id, interest))
    }


    override suspend fun signUp(body: JsonObject): UseCaseResult<BaseResponse> {
        return handleResult(api.signUp(body))
    }

    override suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse> {
        return handleResult(api.updateToken(body))
    }

    override suspend fun getMoim(): UseCaseResult<BaseResponse> {
        return handleResult(api.getMoim())
    }

    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): UseCaseResult<BaseResponse> {
        return handleResult(api.checkAppVersion(packageName, versionCode, versionName))
    }

    private fun handleResult(response: Response<BaseResponse>): UseCaseResult<BaseResponse> {
        return try {
            if (response.isSuccessful) {
                UseCaseResult.Success(response.body()!!)
            } else {
                UseCaseResult.Fail(response.code())
            }
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }
}
