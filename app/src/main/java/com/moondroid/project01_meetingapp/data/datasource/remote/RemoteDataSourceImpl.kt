package com.moondroid.project01_meetingapp.data.datasource.remote

import com.moondroid.project01_meetingapp.data.common.DataSourceException
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.data.common.RequestErrorHandler
import com.moondroid.project01_meetingapp.data.common.DMResult
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.utils.GroupType
import retrofit2.Response

class RemoteDataSourceImpl(private val apiInterface: ApiInterface) : RemoteDataSource {
    override suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.checkAppVersion(
                packageName = packageName,
                versionCode = versionCode,
                versionName = versionName
            )
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun signUp(body: JsonObject): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.signUp(body)
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun signIn(body: JsonObject): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.signIn(body)
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }


    override suspend fun getSalt(id: String): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.getSalt(id)
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }


    override suspend fun signInSocial(body: JsonObject): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.signInSocial(body)
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun updateToken(body: JsonObject): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.updateToken(body)
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }

    }

    override suspend fun getGroup(): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.getGroup()
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun getGroup(id: String, type: GroupType): DMResult<BaseResponse> {
        return try {
            val result = when (type) {
                GroupType.FAVORITE -> apiInterface.getFavorite(id)
                GroupType.RECENT ->  apiInterface.getRecent(id)
                GroupType.MY_GROUP ->  apiInterface.getMyGroup(id)
            }
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun getMoim(): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.getMoim()
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }

    override suspend fun updateInterest(id: String, interest: String): DMResult<BaseResponse> {
        return try {
            val result = apiInterface.updateInterest(id, interest)
            if (result.isSuccessful) {
                result.body()?.let {
                    DMResult.Success(it)
                } ?: run {
                    DMResult.Error(DataSourceException.Server(R.string.no_results_found))
                }
            } else {
                DMResult.Error(
                    DataSourceException.Unexpected(R.string.error_unexpected_message)
                )
            }
        } catch (e: Exception) {
            DMResult.Error(RequestErrorHandler.getRequestError(e))
        }
    }
}