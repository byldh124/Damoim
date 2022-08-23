package com.moondroid.project01_meetingapp.di

import com.moondroid.project01_meetingapp.network.ApiInterface
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        api: ApiInterface
    ): Repository {
        return RepositoryImpl(
            api
        )
    }
}