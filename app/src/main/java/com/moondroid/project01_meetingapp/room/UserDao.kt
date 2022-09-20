package com.moondroid.project01_meetingapp.room

import androidx.room.*
import com.moondroid.project01_meetingapp.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(user: User)

    @Query("SELECT * FROM DMUser")
    fun getUser(): List<User>

    @Delete
    fun delete(user: User)
}