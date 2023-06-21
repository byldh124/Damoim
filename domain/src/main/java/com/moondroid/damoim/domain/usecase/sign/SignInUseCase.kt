package com.moondroid.damoim.domain.usecase.sign

import com.google.gson.JsonObject
import com.moondroid.damoim.domain.repository.SignRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val signRepository: SignRepository) {

    suspend fun signIn(id:String, hashPw: String) = signRepository.signIn(id, hashPw)
}