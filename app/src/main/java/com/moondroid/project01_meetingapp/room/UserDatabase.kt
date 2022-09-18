package com.moondroid.project01_meetingapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moondroid.project01_meetingapp.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}