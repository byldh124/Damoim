package com.moondroid.damoim.domain.usecase.profile

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun execute(body: JsonObject) = signUp(body)
    suspend operator fun invoke(body: JsonObject) = signUp(body)
    private suspend fun signUp(body: JsonObject) = profileRepository.updateToken(body)
}