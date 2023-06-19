package com.moondroid.damoim.domain.usecase.sign

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val signRepository: SignRepository) {

    suspend fun execute(body: JsonObject) = signUp(body)

    suspend operator fun invoke(body: JsonObject) = signUp(body)

    private suspend fun signUp(body: JsonObject) = signRepository.signUp(body)
}