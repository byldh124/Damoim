package com.moondroid.project01_meetingapp.data.datasource.local

import com.moondroid.project01_meetingapp.data.datasource.local.room.UserDao
import com.moondroid.project01_meetingapp.domain.model.User

class LocalDataSource constructor(private val userDao: UserDao) {

    fun getUser() = userDao.getUser()
    fun delete() = userDao.delete()
    fun insertUser(user: User) = userDao.insertData(user)
}
