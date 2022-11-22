package com.moondroid.project01_meetingapp.data.datasource.local.room

import androidx.room.*
import com.moondroid.project01_meetingapp.data.datasource.local.entity.UserEntity
import com.moondroid.project01_meetingapp.domain.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(user: User)

    @Query("SELECT * FROM DMUser")
    fun getUser(): List<UserEntity>

    @Query("DELETE FROM DMUser")
    fun delete()
}