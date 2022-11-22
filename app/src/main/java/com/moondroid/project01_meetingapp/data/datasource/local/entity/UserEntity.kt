package com.moondroid.project01_meetingapp.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moondroid.project01_meetingapp.utils.DM_USER
import com.moondroid.project01_meetingapp.utils.RequestParam

/**
 * User Model class for Room
 * */
@Entity(tableName = DM_USER)
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RequestParam.ID)
    val id: String,
    @ColumnInfo(name = RequestParam.NAME)
    val name: String,
    @ColumnInfo(name = RequestParam.BIRTH)
    val birth: String,
    @ColumnInfo(name = RequestParam.GENDER)
    val gender: String,
    @ColumnInfo(name = RequestParam.LOCATION)
    val location: String,
    @ColumnInfo(name = RequestParam.INTEREST)
    val interest: String,
    @ColumnInfo(name = RequestParam.THUMB)
    val thumb: String,
    @ColumnInfo(name = RequestParam.MESSAGE)
    val message: String
)
