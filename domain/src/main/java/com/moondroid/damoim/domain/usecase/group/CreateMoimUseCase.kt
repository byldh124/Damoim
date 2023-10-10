package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class CreateMoimUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend fun execute(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ) = repository.createMoim(title, address, date, time, pay, lat, lng, joinMember)

    suspend operator fun invoke(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ) = repository.createMoim(title, address, date, time, pay, lat, lng, joinMember)

    private suspend fun createMoim(
        title: String,
        address: String,
        date: String,
        time: String,
        pay: String,
        lat: Double,
        lng: Double,
        joinMember: String
    ) = repository.createMoim(title, address, date, time, pay, lat, lng, joinMember)
}