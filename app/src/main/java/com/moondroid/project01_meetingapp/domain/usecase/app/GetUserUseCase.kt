package com.moondroid.project01_meetingapp.domain.usecase.app

import com.moondroid.project01_meetingapp.domain.repository.AppRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val appRepository: AppRepository) {

    suspend fun execute() = getUser()

    suspend operator fun invoke() = getUser()

    private suspend fun getUser() = appRepository.getUser()
}