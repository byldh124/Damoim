package com.moondroid.project01_meetingapp.room

import androidx.room.*
import com.moondroid.project01_meetingapp.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(user: User)

    @Query("SELECT * FROM DMUser")
    suspend fun getUser(): List<User>

    @Delete
    suspend fun delete(user: User)
}