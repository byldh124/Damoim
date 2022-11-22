package com.moondroid.project01_meetingapp.domain.usecase.sign

import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.domain.repository.SignRepository
import javax.inject.Inject

class GetSaltUseCase @Inject constructor(private val signRepository: SignRepository) {
    suspend fun execute(id: String) = getSalt(id)

    suspend operator fun invoke(id: String) = getSalt(id)

    private suspend fun getSalt(id: String) = signRepository.getSalt(id)
}