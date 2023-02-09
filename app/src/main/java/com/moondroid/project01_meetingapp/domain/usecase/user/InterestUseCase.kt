package com.moondroid.project01_meetingapp.domain.usecase.user

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.domain.repository.UserRepository
import javax.inject.Inject

class InterestUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(interest: String) = update(interest)
    suspend operator fun invoke(interest: String) = update(interest)
    private suspend fun update(interest: String) = userRepository.updateInterest(interest)
}