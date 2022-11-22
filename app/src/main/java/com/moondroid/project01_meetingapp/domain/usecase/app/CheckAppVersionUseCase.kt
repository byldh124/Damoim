package com.moondroid.project01_meetingapp.domain.usecase.app

import com.moondroid.project01_meetingapp.domain.repository.AppRepository
import javax.inject.Inject

class CheckAppVersionUseCase @Inject constructor(private val appRepository: AppRepository) {

    suspend fun execute(packageName: String, versionCode: Int, versionName: String) = checkAppVersion(
        packageName, versionCode, versionName
    )

    suspend operator fun invoke(packageName: String, versionCode: Int, versionName: String) = checkAppVersion(
        packageName, versionCode, versionName
    )

    private suspend fun checkAppVersion(
        packageName: String,
        versionCode: Int,
        versionName: String
    ) = appRepository.checkAppVersion(
        packageName, versionCode, versionName
    )
}