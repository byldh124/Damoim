package com.moondroid.damoim.domain.usecase.sign

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val signRepository: SignRepository) {

    suspend fun execute(body: JsonObject) = signIn(body)

    suspend operator fun invoke(body: JsonObject) = signIn(body)

    private suspend fun signIn(body: JsonObject) = signRepository.signIn(body)
}