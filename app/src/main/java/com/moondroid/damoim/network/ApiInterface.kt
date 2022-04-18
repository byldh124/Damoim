package com.moondroid.damoim.network

import com.moondroid.damoim.model.BaseResponse
import com.moondroid.damoim.network.URLManager.GET_GROUP
import com.moondroid.damoim.network.URLManager.GET_MEMBER
import com.moondroid.damoim.network.URLManager.SIGN_IN
import com.moondroid.damoim.network.URLManager.SIGN_IN_WITH_KAKAO
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET(GET_GROUP)
    fun getGroup(): Deferred<BaseResponse>

    @GET(GET_MEMBER)
    fun getMember(@Query("meetName") groupName: String):Deferred<BaseResponse>

    @POST(SIGN_IN)
    fun signIn(@Query("userId") userId: String): Deferred<BaseResponse>

    @POST(SIGN_IN_WITH_KAKAO)
    fun signInWithKakao(
        @Query("userId") userId: String,
        @Query("userName") userName: String,
        @Query("userProfileImgUrl") userProfileImgUrl: String
    ): Deferred<BaseResponse>
}
