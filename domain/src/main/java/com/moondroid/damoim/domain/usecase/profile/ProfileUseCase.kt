package com.moondroid.damoim.domain.usecase.profile

import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend fun execute() = getProfile()
    suspend operator fun invoke() = getProfile()
    private suspend fun getProfile() = profileRepository.getProfile()

    fun execute(profile: Profile) = saveProfile(profile)
    operator fun invoke(profile: Profile) = saveProfile(profile)
    private fun saveProfile(profile: Profile) = profileRepository.saveProfile(profile)
}