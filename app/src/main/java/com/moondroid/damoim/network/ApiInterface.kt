package com.moondroid.damoim.network

import com.moondroid.damoim.model.BaseResponse
import com.moondroid.damoim.model.BoolResponse
import com.moondroid.damoim.model.MsgResponse
import com.moondroid.damoim.network.URLManager.CHECK_ID
import com.moondroid.damoim.network.URLManager.GET_GROUP
import com.moondroid.damoim.network.URLManager.GET_MEMBER
import com.moondroid.damoim.network.URLManager.GET_MY_GROUP
import com.moondroid.damoim.network.URLManager.SIGN_IN
import com.moondroid.damoim.network.URLManager.SIGN_IN_WITH_KAKAO
import com.moondroid.damoim.network.URLManager.SIGN_UP
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET(GET_GROUP)
    fun getGroup(): Deferred<BaseResponse>

    @GET(GET_MY_GROUP)
    fun getMyGroup(@Query("userId") userId: String): Deferred<BaseResponse>

    @GET(GET_MEMBER)
    fun getMember(@Query("meetName") groupName: String):Deferred<BaseResponse>

    @POST(SIGN_IN)
    fun signIn(@Query("userId") userId: String): Deferred<BaseResponse>

    @POST(SIGN_UP)
    fun signUp(
        @Query("userId") userId: String,
        @Query("userId") userName: String,
        @Query("userId") userBirthDate: String,
        @Query("userId") userGender: String,
        @Query("userId") userLocation: String,
        @Query("userId") userInterest: String,
        @Query("userId") userProfileImgUrl: String,
        @Query("userId") userProfileMessage: String,
        @Query("userId") FCMToken: String,
    ) : Deferred<MsgResponse>

    @GET(CHECK_ID)
    fun checkId(
        @Query("userId") id: String,
    ) : Deferred<BoolResponse>

    @POST(SIGN_IN_WITH_KAKAO)
    fun signInWithKakao(
        @Query("userId") userId: String,
        @Query("userName") userName: String,
        @Query("userProfileImgUrl") userProfileImgUrl: String
    ): Deferred<BaseResponse>
}
