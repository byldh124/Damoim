package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

interface Repository {

    /** 앱 기능 관련 **/

    suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): UseCaseResult<BaseResponse>  // 앱 버전 정보 체크


    /** 그룹 정보 관련 */
    suspend fun loadGroup(): UseCaseResult<BaseResponse>                        // 전체 그룹
    suspend fun getMyGroup(userId: String): UseCaseResult<BaseResponse>         // 특정 유저가 가입한 그룹
    suspend fun getFavorite(id: String): UseCaseResult<BaseResponse>            // 특정 유저가 관심목록 표시한 그룹
    suspend fun getRecent(id: String): UseCaseResult<BaseResponse>              // 특정 유저가 최근 본 그룹
    suspend fun loadMember(title: String): UseCaseResult<BaseResponse>          // 특정 그룹에 가입된 유저


    /** 유저 그룹 -----------------------------------**/
    suspend fun saveRecent(
        id: String, title: String, lastTime: String
    ): UseCaseResult<BaseResponse>  // 최근 모임 저장

    suspend fun saveFavor(
        id: String, title: String, active: Boolean
    ): UseCaseResult<BaseResponse>  // 관심 모임 저장

    suspend fun join(
        id: String, title: String
    ): UseCaseResult<BaseResponse>  // 모입 가입

    suspend fun getFavor(
        id: String, title: String
    ): UseCaseResult<BaseResponse>  // 관심 모임 확인


    /** 그룹 생성, 수정 **/
    suspend fun createGroup(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse>  // 그룹 생성

    suspend fun updateGroup(
        body: Map<String, RequestBody>, thumb: MultipartBody.Part?, image: MultipartBody.Part?
    ): UseCaseResult<BaseResponse>  // 그룹 생성


    /** 회원가입, 로그인 **/
    suspend fun signUp(body: JsonObject): UseCaseResult<BaseResponse>           // 회원가입
    suspend fun signIn(body: JsonObject): UseCaseResult<BaseResponse>           // 로그인
    suspend fun getSalt(id: String): UseCaseResult<BaseResponse>                // 로그인 관련
    suspend fun signInKakao(body: JsonObject): UseCaseResult<BaseResponse>      // 카카오 로그인


    /** 유저 정보 수정 **/
    suspend fun updateProfile(
        body: Map<String, RequestBody>,
        file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> // 회원 정보 수정

    suspend fun updateInterest(id: String, interest: String): UseCaseResult<BaseResponse> // 관심사 변경

    suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse>          // FCM 토큰 변경

    /** 모임 관련 **/
    suspend fun getMoim(): UseCaseResult<BaseResponse>                              // 모임 정보 로드
    suspend fun getMoim(title: String): UseCaseResult<BaseResponse>                 // 모임 정보 로드
    suspend fun createMoim(body: JsonObject): UseCaseResult<BaseResponse>           // 모임 만들기
    suspend fun getMoimMember(joinMember: String): UseCaseResult<BaseResponse>      // 정모 멤버
    suspend fun joinInMoim(id: String, title: String, date: String): UseCaseResult<BaseResponse>  // 정모 참여
}

class RepositoryImpl @Inject constructor(private val api: ApiInterface) : Repository {
    override suspend fun loadGroup(): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getGroup())
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }

    }

    override suspend fun getMyGroup(userId: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getMyGroup(userId))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun loadMember(title: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getMember(title))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun saveRecent(
        id: String, title: String, lastTime: String
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.saveRecent(id, title, lastTime))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }

    }

    override suspend fun saveFavor(
        id: String, title: String, active: Boolean
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.saveFavor(id, title, active))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun join(id: String, title: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.join(id, title))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getFavor(id: String, title: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getFavor(id, title))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }


    override suspend fun createGroup(
        body: Map<String, RequestBody>, file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.createGroup(body, file))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateGroup(
        body: Map<String, RequestBody>, thumb: MultipartBody.Part?, image: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.updateGroup(body, thumb, image))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signIn(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.signIn(body))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getSalt(id: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getSalt(id))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun signInKakao(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.signInKakao(body))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateProfile(
        body: Map<String, RequestBody>, file: MultipartBody.Part?
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.updateProfile(body, file))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getFavorite(id: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getFavorite(id))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getRecent(id: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getRecent(id))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateInterest(id: String, interest: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.updateInterest(id, interest))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }


    override suspend fun signUp(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.signUp(body))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun updateToken(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.updateToken(body))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getMoim(): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getMoim())
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getMoim(title: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getMoim(title))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun createMoim(body: JsonObject): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.createMoim(body))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun getMoimMember(joinMember: String): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.getMoimMember(joinMember))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun joinInMoim(
        id: String, title: String, date: String
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.joinInMoim(id, title, date))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    override suspend fun checkAppVersion(
        packageName: String, versionCode: Int, versionName: String
    ): UseCaseResult<BaseResponse> {
        return try {
            handleResult(api.checkAppVersion(packageName, versionCode, versionName))
        } catch (e: Exception) {
            DMCrash.logException(e)
            UseCaseResult.Error(e)
        }
    }

    private fun handleResult(response: Response<BaseResponse>): UseCaseResult<BaseResponse> {
        return if (response.isSuccessful) {
            UseCaseResult.Success(response.body()!!)
        } else {
            UseCaseResult.Fail(response.code())
        }
    }
}
