package com.moondroid.project01_meetingapp.network

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.model.BaseResponse
import com.moondroid.project01_meetingapp.network.URLManager.ChECK_APP_VERSION
import com.moondroid.project01_meetingapp.network.URLManager.GET_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.GET_MEMBER
import com.moondroid.project01_meetingapp.network.URLManager.GET_MY_GROUP
import com.moondroid.project01_meetingapp.network.URLManager.GET_SALT
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN_KAKAO
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_IN_WITH_KAKAO
import com.moondroid.project01_meetingapp.network.URLManager.SIGN_UP
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_PROFILE
import com.moondroid.project01_meetingapp.network.URLManager.UPDATE_TOKEN
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

    @GET(GET_GROUP)
    fun getGroup(): Deferred<BaseResponse>

    @GET(GET_MY_GROUP)
    fun getMyGroup(@Query("userId") userId: String): Deferred<BaseResponse>

    @GET(GET_MEMBER)
    fun getMember(@Query("meetName") groupName: String):Deferred<BaseResponse>

    @POST(SIGN_IN)
    fun signIn(
        @Body body: JsonObject
    ): Deferred<BaseResponse>

    @GET(GET_SALT)
    fun getSalt(
        @Query ("id") id: String
    ) : Deferred<BaseResponse>

    @POST(SIGN_UP)
    fun signUp(
        @Body body: JsonObject
    ) : Deferred<BaseResponse>

    @POST(SIGN_IN_WITH_KAKAO)
    fun signInWithKakao(
        @Query("userId") userId: String,
        @Query("userName") userName: String,
        @Query("userProfileImgUrl") userProfileImgUrl: String
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
    ) : Deferred<BaseResponse>

    @POST(SIGN_IN_KAKAO)
    fun signInkakao(
        @Body body: JsonObject
    ) : Deferred<BaseResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_PROFILE)
    fun updateProfile(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ) : Deferred<BaseResponse>
}
