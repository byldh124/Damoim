package com.moondroid.project01_meetingapp.domain.usecase.group

import com.moondroid.project01_meetingapp.domain.repository.GroupRepository
import com.moondroid.project01_meetingapp.utils.GroupType
import javax.inject.Inject

class MoimUseCase @Inject constructor(private val groupRepository: GroupRepository){
    suspend fun execute() = getMoim()

    suspend operator fun invoke() = getMoim()

    private suspend fun getMoim() = groupRepository.getMoim()

}