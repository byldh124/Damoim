package com.moondroid.damoim.data.model.dao

import androidx.room.*
import com.moondroid.damoim.data.model.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("SELECT * FROM DMUser")
    suspend fun getProfile(): List<ProfileEntity>

    @Query("DELETE FROM DMUser")
    suspend fun deleteProfileAll()
}