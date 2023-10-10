package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetMoimsUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend fun execute(title: String) = repository.getMoims(title)

    suspend operator fun invoke(title: String) = repository.getMoims(title)

    private suspend fun getMoims(title: String) = repository.getMoims(title)
}