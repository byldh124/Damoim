package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class MoimUseCase @Inject constructor(private val repository: GroupRepository){
    suspend fun execute() = getMoim()

    suspend operator fun invoke() = getMoim()

    private suspend fun getMoim() = repository.getMoim()

}