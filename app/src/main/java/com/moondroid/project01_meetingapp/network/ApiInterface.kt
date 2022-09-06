package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.URLManager.CREATE_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.CREATE_MOIM
import com.moondroid.project01_meetingapp.network.URLManager.ChECK_APP_VERSION
import com.moondroid.project01_meetingapp.network.URLManager.GET_FAVOR
import com.moondroid.project01_meetingapp.network.URLManager.GET_FAVORITE
import com.moondroid.project01_meetingapp.network.URLManager.GET_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.GET_MEMBER
import com.moondroid.project01_meetingapp.network.URLManager.GET_MOIM
import com.moondroid.project01_meetingapp.network.URLManager.GET_MOIM_MEMBER
import com.moondroid.project01_meetingapp.network.URLManager.GET_MY_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.GET_RECENT
import com.moondroid.project01_meetingapp.network.URLManager.JOIN
import com.moondroid.project01_meetingapp.network.URLManager.JOIN_INTO_MOIM
import com.moondroid.project01_meetingapp.network.URLManager.SALT
import com.moondroid.project01_meetingapp.network.URLManager.SAVE_FAVOR
import com.moondroid.project01_meetingapp.network.URLManager.SAVE_RECENT
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN_KAKAO
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_UP
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_INTEREST
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_PROFILE
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_TOKEN
import com.moondroid.project01_meetingapp.utils.RequestParam
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET(GET_GROUP)
    suspend fun getGroup(): Response<BaseResponse>

    @GET(GET_MY_GROUP)
    suspend fun getMyGroup(@Query(RequestParam.ID) userId: String): Response<BaseResponse>

    @GET(GET_MEMBER)
    suspend fun getMember(@Query(RequestParam.TITLE) title: String): Response<BaseResponse>

    @POST(SIGN_IN)
    suspend fun signIn(
        @Body body: JsonObject
    ): Response<BaseResponse>

    @GET(SALT)
    suspend fun getSalt(
        @Query(RequestParam.ID) id: String
    ): Response<BaseResponse>

    @POST(SIGN_UP)
    suspend fun signUp(
        @Body body: JsonObject
    ): Response<BaseResponse>

    @GET(ChECK_APP_VERSION)
    suspend fun checkAppVersion(
        @Query("packageName") packageName: String,
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String
    ): Response<BaseResponse>

    @POST(UPDATE_TOKEN)
    suspend fun updateToken(
        @Body body: JsonObject
    ): Response<BaseResponse>

    @GET(GET_MOIM)
    suspend fun getMoim(): Response<BaseResponse>

    @POST(CREATE_MOIM)
    suspend fun createMoim(@Body body: JsonObject): Response<BaseResponse>

    @GET(GET_MOIM)
    suspend fun getMoim(
        @Query(RequestParam.TITLE) title: String
    ): Response<BaseResponse>

    @POST(SIGN_IN_KAKAO)
    suspend fun signInkakao(
        @Body body: JsonObject
    ): Response<BaseResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Response<BaseResponse>

    @GET(UPDATE_INTEREST)
    suspend fun updateInterest(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.INTEREST) interest: String
    ): Response<BaseResponse>

    @GET(GET_FAVORITE)
    suspend fun getFavortite(
        @Query(RequestParam.ID) id: String
    ): Response<BaseResponse>

    @GET(GET_RECENT)
    suspend fun getRecent(
        @Query(RequestParam.ID) id: String
    ): Response<BaseResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(CREATE_GROUP)
    suspend fun createGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Response<BaseResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_GROUP)
    suspend fun updateGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part thumb: MultipartBody.Part?,
        @Part image: MultipartBody.Part?
    ): Response<BaseResponse>

    @GET(SAVE_RECENT)
    suspend fun saveRecent(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.LAST_TIME) lastTime: String
    ): Response<BaseResponse>

    @GET(SAVE_FAVOR)
    suspend fun saveFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.ACTIVE) active: Boolean
    ): Response<BaseResponse>

    @GET(JOIN)
    suspend fun join(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String
    ): Response<BaseResponse>

    @GET(GET_FAVOR)
    suspend fun getFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String
    ): Response<BaseResponse>

    @GET(GET_MOIM_MEMBER)
    suspend fun getMoimMember(
        @Query(RequestParam.JOIN_MEMBER) joinMember: String
    ): Response<BaseResponse>

    @GET(JOIN_INTO_MOIM)
    suspend fun joinInMoim(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.DATE) date: String
    ): Response<BaseResponse>
}
