package com.moondroid.project01_meetingapp.domain.usecase.user

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.domain.repository.AppRepository
import com.moondroid.project01_meetingapp.domain.repository.UserRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(body: JsonObject) = signUp(body)
    suspend operator fun invoke(body: JsonObject) = signUp(body)
    private suspend fun signUp(body: JsonObject) = userRepository.updateToken(body)
}