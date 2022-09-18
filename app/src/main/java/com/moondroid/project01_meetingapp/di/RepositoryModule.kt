package com.moondroid.project01_meetingapp.di

import com.moondroid.project01_meetingapp.network.ApiInterface
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.RepositoryImpl
import com.moondroid.project01_meetingapp.room.UserDao
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
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        api: ApiInterface,
        userDao: UserDao
    ): Repository {
        return RepositoryImpl(api, userDao)
    }
}