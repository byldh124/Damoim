package com.moondroid.project01_meetingapp.data.datasource.local

import com.moondroid.project01_meetingapp.data.datasource.local.room.UserDao
import com.moondroid.project01_meetingapp.domain.model.User

class LocalDataSource constructor(private val userDao: UserDao) {

    fun getUser() : User?{
        val list = userDao.getUser()
        return if (list.isNotEmpty()) {
            list.last()
        } else {
            null
        }
    }

    fun delete() = userDao.delete()
    fun insertUser(user: User) = userDao.insertData(user)
}
