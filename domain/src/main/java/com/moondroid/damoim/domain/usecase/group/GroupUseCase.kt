package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GroupUseCase  @Inject constructor(private val groupRepository: GroupRepository){
    suspend fun execute() = getGroup()

    suspend operator fun invoke() = getGroup()

    private suspend fun getGroup() = groupRepository.getGroup()

    suspend fun execute(type: GroupType) = getGroup(type)

    suspend operator fun invoke(type: GroupType) = getGroup(type)

    private suspend fun getGroup(type: GroupType) = groupRepository.getGroup(type)
}