package com.moondroid.project01_meetingapp.data.response

import com.moondroid.project01_meetingapp.data.datasource.local.entity.UserEntity
import com.moondroid.project01_meetingapp.domain.model.DMUser
import com.moondroid.project01_meetingapp.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    birth = birth,
    gender = gender,
    location = location,
    interest = interest,
    thumb = thumb,
    message = message
)

fun UserResult.toDomain(): User = User(
    id = id,
    name = name,
    birth = birth,
    gender = gender,
    location = location,
    interest = interest,
    thumb = thumb,
    message = message
)

fun UserResult.toEntity() = UserEntity(
    id = id,
    name = name,
    birth = birth,
    gender = gender,
    location = location,
    interest = interest,
    thumb = thumb,
    message = message
)