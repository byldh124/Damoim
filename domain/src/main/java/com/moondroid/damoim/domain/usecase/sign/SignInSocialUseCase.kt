package com.moondroid.damoim.domain.usecase.sign

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignInSocialUseCase @Inject constructor(private val signRepository: SignRepository) {

    suspend fun execute(body: JsonObject) = signInSocial(body)

    suspend operator fun invoke(body: JsonObject) = signInSocial(body)

    private suspend fun signInSocial(body: JsonObject) = signRepository.signInSocial(body)
}