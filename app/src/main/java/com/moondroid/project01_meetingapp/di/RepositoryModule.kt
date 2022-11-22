package com.moondroid.project01_meetingapp.di

import com.moondroid.project01_meetingapp.data.datasource.local.LocalDataSource
import com.moondroid.project01_meetingapp.data.datasource.remote.ApiInterface
import com.moondroid.project01_meetingapp.data.datasource.remote.RemoteDataSourceImpl
import com.moondroid.project01_meetingapp.data.repository.AppRepositoryImpl
import com.moondroid.project01_meetingapp.data.repository.SignRepositoryImpl
import com.moondroid.project01_meetingapp.domain.repository.AppRepository
import com.moondroid.project01_meetingapp.domain.repository.SignRepository
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 아직은 MainRepository 하나만 사용할 예정
 * 추후 프로젝트 단위가 커지면 각각에 맞는 Repository 분할 필요
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMainRepository(
        api: ApiInterface
    ): Repository {
        return RepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSignRepository(
        api: ApiInterface,
        localDataSource: LocalDataSource
    ) : SignRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl(api)
        return SignRepositoryImpl(remoteDataSource = remoteDataSourceImpl, localDataSource = localDataSource)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        api: ApiInterface,
        localDataSource: LocalDataSource
    ) : AppRepository {
        val remoteDataSourceImpl = RemoteDataSourceImpl(api)
        return AppRepositoryImpl(remoteDataSource = remoteDataSourceImpl, localDataSource = localDataSource)
    }
}


