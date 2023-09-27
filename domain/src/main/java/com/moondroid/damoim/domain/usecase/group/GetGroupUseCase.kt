package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.common.GroupType
import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupUseCase  @Inject constructor(private val repository: GroupRepository){
    suspend fun execute(type: GroupType) = getGroup(type)

    suspend operator fun invoke(type: GroupType) = getGroup(type)

    private suspend fun getGroup(type: GroupType) = repository.getGroupList(type)
}