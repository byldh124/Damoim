package com.moondroid.damoim.data.repository

import com.google.gson.JsonObject
import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.common.RequestParam
import com.moondroid.damoim.data.datasource.remote.RemoteDataSource
import com.moondroid.damoim.data.model.dao.ProfileDao
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.damoim.domain.model.MoimItem
import com.moondroid.damoim.data.mapper.DataMapper.toGroupItem
import com.moondroid.damoim.data.mapper.DataMapper.toMoimItem
import com.moondroid.damoim.domain.model.status.ApiResult
import com.moondroid.damoim.domain.repository.GroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: ProfileDao
) : GroupRepository {
    override suspend fun getGroupList(type: GroupType): Flow<ApiResult<List<GroupItem>>> =
        flow<ApiResult<List<GroupItem>>> {
            localDataSource.getProfile().run {
                this?.let { profile ->
                    remoteDataSource.getGroupList(profile.id, type).run {
                        when (this) {
                            is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toGroupItem() }))
                            is ApiResult.Fail -> emit(ApiResult.Fail(code))
                            is ApiResult.Error -> emit(ApiResult.Error(throwable))
                        }
                    }
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getMoim(): Flow<ApiResult<List<MoimItem>>> = flow {
        remoteDataSource.getMoimList().run {
            when (this) {
                is ApiResult.Error -> emit(ApiResult.Error(throwable))
                is ApiResult.Fail -> emit(ApiResult.Fail(code))
                is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toMoimItem() }))
            }
        }
    }

    override suspend fun createGroup(
        id: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        file: File
    ): Flow<ApiResult<GroupItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateGroup(
        originTitle: String,
        title: String,
        location: String,
        purpose: String,
        interest: String,
        information: String,
        thumb: File?,
        image: File?
    ): Flow<ApiResult<GroupItem>> {
        val body = HashMap<String, RequestBody>()
        body[RequestParam.ORIGIN_TITLE] = originTitle.toRequestBody()
        body[RequestParam.TITLE] = title.toRequestBody()
        body[RequestParam.LOCATION] = location.toRequestBody()
        body[RequestParam.PURPOSE] = purpose.toRequestBody()
        body[RequestParam.INTEREST] = interest.toRequestBody()
        body[RequestParam.INFORMATION] = information.toRequestBody()

        var thumbPart: MultipartBody.Part? = null
        thumb?.let { file ->
            val requestBody = file.asRequestBody("image/*".toMediaType())
            thumbPart = MultipartBody.Part.createFormData("thumb", file.name, requestBody)
        }

        var introPart: MultipartBody.Part? = null
        thumb?.let { file ->
            val requestBody = file.asRequestBody("image/*".toMediaType())
            introPart = MultipartBody.Part.createFormData("intro", file.name, requestBody)
        }
        return flow {
            remoteDataSource.updateGroup(body, thumbPart, introPart).run {
                when (this) {
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Success -> emit(ApiResult.Success(response.toGroupItem()))
                }
            }
        }
    }

    override suspend fun createMoim(body: JsonObject): Flow<ApiResult<MoimItem>> {
        TODO("Not yet implemented")
    }

}