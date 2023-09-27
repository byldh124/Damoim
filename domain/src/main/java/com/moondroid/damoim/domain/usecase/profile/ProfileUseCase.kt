package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: ProfileRepository) {

    suspend fun execute() = getProfile()
    suspend operator fun invoke() = getProfile()
    private suspend fun getProfile() = repository.getProfile()
}