package com.moondroid.damoim.network

import android.icu.text.DateFormat.Field.SECOND
import com.google.gson.JsonObject
import com.moondroid.damoim.model.BaseResponse
import com.moondroid.damoim.network.URLManager.GET_GROUP
import com.moondroid.damoim.network.URLManager.GET_MEMBER
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @GET(GET_GROUP)
    fun getGroup(): Call<BaseResponse>

    @GET(GET_MEMBER)
    fun getMember(@Query("meetName") groupName: String):Call<BaseResponse>

}
