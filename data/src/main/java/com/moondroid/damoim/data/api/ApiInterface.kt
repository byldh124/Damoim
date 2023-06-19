package com.moondroid.damoim.data.api

import com.google.gson.JsonObject
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.api.URLManager.BLOCK
import com.moondroid.damoim.data.api.URLManager.CREATE_GROUP
import com.moondroid.damoim.data.api.URLManager.CREATE_MOIM
import com.moondroid.damoim.data.api.URLManager.ChECK_APP_VERSION
import com.moondroid.damoim.data.api.URLManager.GET_FAVOR
import com.moondroid.damoim.data.api.URLManager.GET_FAVORITE
import com.moondroid.damoim.data.api.URLManager.GET_GROUP
import com.moondroid.damoim.data.api.URLManager.GET_MEMBER
import com.moondroid.damoim.data.api.URLManager.GET_MOIM
import com.moondroid.damoim.data.api.URLManager.GET_MOIM_MEMBER
import com.moondroid.damoim.data.api.URLManager.GET_MY_GROUP
import com.moondroid.damoim.data.api.URLManager.GET_RECENT
import com.moondroid.damoim.data.api.URLManager.JOIN
import com.moondroid.damoim.data.api.URLManager.JOIN_INTO_MOIM
import com.moondroid.damoim.data.api.URLManager.REPORT
import com.moondroid.damoim.data.api.URLManager.SALT
import com.moondroid.damoim.data.api.URLManager.SAVE_FAVOR
import com.moondroid.damoim.data.api.URLManager.SAVE_RECENT
import com.moondroid.damoim.data.api.URLManager.SIGN_IN
import com.moondroid.damoim.data.api.URLManager.SIGN_IN_SOCIAL
import com.moondroid.damoim.data.api.URLManager.SIGN_UP
import com.moondroid.damoim.data.api.URLManager.UPDATE_GROUP
import com.moondroid.damoim.data.api.URLManager.UPDATE_INTEREST
import com.moondroid.damoim.data.api.URLManager.UPDATE_PROFILE
import com.moondroid.damoim.data.api.URLManager.UPDATE_TOKEN
import com.moondroid.damoim.data.model.dto.BaseResponseDTO
import com.moondroid.damoim.data.model.response.GroupResponse
import com.moondroid.damoim.data.model.response.ProfileResponse
import com.moondroid.damoim.data.model.response.SaltResponse
import com.moondroid.damoim.domain.model.status.ApiStatus
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiInterface {

    @GET(GET_GROUP)
    suspend fun getGroup(): ApiStatus<GroupResponse>

    @GET(GET_MY_GROUP)
    suspend fun getMyGroup(@Query(RequestParam.ID) userId: String): ApiStatus<GroupResponse>

    @GET(GET_MEMBER)
    suspend fun getMember(@Query(RequestParam.TITLE) title: String): ApiStatus<BaseResponseDTO>

    @POST(SIGN_IN)
    suspend fun signIn(
        @Body body: JsonObject
    ): ApiStatus<ProfileResponse>

    @GET(SALT)
    suspend fun getSalt(
        @Query(RequestParam.ID) id: String
    ): ApiStatus<SaltResponse>

    /**
     * @param body (id , hashPw, salt, gender, location, interest, message, thumb : String)
     *
     **/
    @POST(SIGN_UP)
    suspend fun signUp(
        @Body body: JsonObject
    ): ApiStatus<ProfileResponse>

    @GET(ChECK_APP_VERSION)
    suspend fun checkAppVersion(
        @Query("packageName") packageName: String,
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String
    ): ApiStatus<BaseResponseDTO>

    /**
     * @param body (id: String, token: String)
     **/
    @POST(UPDATE_TOKEN)
    suspend fun updateToken(
        @Body body: JsonObject
    ): ApiStatus<BaseResponseDTO>

    @GET(GET_MOIM)
    suspend fun getMoim(): ApiStatus<BaseResponseDTO>

    @POST(CREATE_MOIM)
    suspend fun createMoim(@Body body: JsonObject): ApiStatus<BaseResponseDTO>

    @GET(GET_MOIM)
    suspend fun getMoim(
        @Query(RequestParam.TITLE) title: String
    ): ApiStatus<BaseResponseDTO>

    @POST(SIGN_IN_SOCIAL)
    suspend fun signInSocial(
        @Body body: JsonObject
    ): ApiStatus<ProfileResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): ApiStatus<BaseResponseDTO>

    @GET(UPDATE_INTEREST)
    suspend fun updateInterest(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.INTEREST) interest: String
    ): ApiStatus<BaseResponseDTO>

    @GET(GET_FAVORITE)
    suspend fun getFavorite(
        @Query(RequestParam.ID) id: String
    ): ApiStatus<GroupResponse>

    @GET(GET_RECENT)
    suspend fun getRecent(
        @Query(RequestParam.ID) id: String
    ): ApiStatus<GroupResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(CREATE_GROUP)
    suspend fun createGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part file: MultipartBody.Part?
    ): ApiStatus<GroupResponse>

    @JvmSuppressWildcards
    @Multipart
    @POST(UPDATE_GROUP)
    suspend fun updateGroup(
        @PartMap body: Map<String, RequestBody>,
        @Part thumb: MultipartBody.Part?,
        @Part image: MultipartBody.Part?
    ): ApiStatus<GroupResponse>

    @GET(SAVE_RECENT)
    suspend fun saveRecent(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.LAST_TIME) lastTime: String
    ): ApiStatus<BaseResponseDTO>

    @GET(SAVE_FAVOR)
    suspend fun saveFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.ACTIVE) active: Boolean
    ): ApiStatus<BaseResponseDTO>

    @GET(JOIN)
    suspend fun join(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String
    ): ApiStatus<BaseResponseDTO>

    @GET(GET_FAVOR)
    suspend fun getFavor(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String
    ): ApiStatus<BaseResponseDTO>

    @GET(GET_MOIM_MEMBER)
    suspend fun getMoimMember(
        @Query(RequestParam.JOIN_MEMBER) joinMember: String
    ): ApiStatus<BaseResponseDTO>

    @GET(JOIN_INTO_MOIM)
    suspend fun joinInMoim(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.TITLE) title: String,
        @Query(RequestParam.DATE) date: String
    ): ApiStatus<BaseResponseDTO>

    @GET(BLOCK)
    suspend fun blockUser(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.BLOCK_ID) blockId: String
    ):ApiStatus<BaseResponseDTO>

    @GET(REPORT)
    suspend fun reportUser(
        @Query(RequestParam.ID) id: String,
        @Query(RequestParam.MESSAGE) msg: String
    ): ApiStatus<BaseResponseDTO>
}
