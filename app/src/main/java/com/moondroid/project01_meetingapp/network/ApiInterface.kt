package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.URLManager.CREATE_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.ChECK_APP_VERSION
import com.moondroid.project01_meetingapp.network.URLManager.GET_FAVORITE
import com.moondroid.project01_meetingapp.network.URLManager.GET_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.GET_MEMBER
import com.moondroid.project01_meetingapp.network.URLManager.GET_MY_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.GET_RECENT
import com.moondroid.project01_meetingapp.network.URLManager.SALT
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN_KAKAO
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_UP
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_INTEREST
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_PROFILE
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_TOKEN
import com.moondroid.project01_meetingapp.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

    @GET(GET_GROUP)
    fun getGroup(): Deferred<BaseResponse>

    @GET(GET_MY_GROUP)
    fun getMyGroup(@Query("id") userId: String): Deferred<BaseResponse>

    @GET(GET_MEMBER)
    fun getMember(@Query("title") groupName: String): Deferred<BaseResponse>

    @POST(SIGN_IN)
    fun signIn(
        @Body body: JsonObject
    ): Deferred<BaseResponse>

    @GET(SALT)
    fun getSalt(
        @Query(Constants.RequestParam.ID) id: String
    ): Deferred<BaseResponse>

    @POST(SIGN_UP)
    fun signUp(
        @Body body: JsonObject
    ): Deferred<BaseResponse>

    @GET(ChECK_APP_VERSION)
    fun checkAppVersion(
        @Query("packageName") packageName: String,
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String
    ): Deferred<BaseResponse>

    @POST(UPDATE_TOKEN)
    fun updateToken(
        @Body body: JsonObject
    ): Deferred<BaseResponse>

    @POST(SIGN_IN_KAKAO)
    fun signInkakao(
        @Body body: JsonObject
    ): Deferred<BaseResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_PROFILE)
    fun updateProfile(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): Deferred<BaseResponse>

    @GET(UPDATE_INTEREST)
    fun updateInterest(
        @Query(Constants.RequestParam.ID) id: String,
        @Query(Constants.RequestParam.INTEREST) interest: String
    ): Deferred<BaseResponse>

    @GET(GET_FAVORITE)
    fun getFavortite(
        @Query(Constants.RequestParam.ID) id: String
    ): Deferred<BaseResponse>

    @GET(GET_RECENT)
    fun getRecent(
        @Query(Constants.RequestParam.ID) id: String
    ): Deferred<BaseResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(CREATE_GROUP)
    fun createGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ) : Deferred<BaseResponse>
}
