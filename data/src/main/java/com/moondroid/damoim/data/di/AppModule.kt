package com.moondroid.damoim.data.di

import android.content.Context
import androidx.room.Room
import com.moondroid.damoim.data.source.local.LocalDatabase
import com.moondroid.damoim.data.mapper.DataTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in Application Component (i.e. everywhere in the application)
    @Provides
    fun provideUserDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        LocalDatabase::class.java,
        "Damoim.db"
    ).addTypeConverter(DataTypeConverter()).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideUserDao(db: LocalDatabase) = db.profileDao() // The reason we can implement a Dao for the database
}