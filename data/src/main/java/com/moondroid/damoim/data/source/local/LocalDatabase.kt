package com.moondroid.damoim.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moondroid.damoim.data.model.entity.ProfileEntity
import com.moondroid.damoim.data.model.dao.ProfileDao

@Database(entities = [ProfileEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}