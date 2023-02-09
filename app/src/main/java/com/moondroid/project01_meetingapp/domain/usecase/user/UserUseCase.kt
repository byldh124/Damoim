package com.moondroid.project01_meetingapp.domain.usecase.user

import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun execute() = getUser()
    suspend operator fun invoke() = getUser()
    private suspend fun getUser() = userRepository.getUser()

    fun execute(user: User) = insertUser(user)
    operator fun invoke(user: User) = insertUser(user)
    private fun insertUser(user: User) = userRepository.insertUser(user)
}