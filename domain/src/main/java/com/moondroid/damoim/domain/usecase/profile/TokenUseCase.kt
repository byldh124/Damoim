package com.moondroid.damoim.domain.usecase.profile

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class TokenUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend fun execute(token: String) = updateToken(token)
    suspend operator fun invoke(token: String) = updateToken(token)
    private suspend fun updateToken(token: String) = profileRepository.updateToken(token)
}