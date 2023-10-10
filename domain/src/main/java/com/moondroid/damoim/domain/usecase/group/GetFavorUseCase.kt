package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class GetFavorUseCase @Inject constructor(private val repository: GroupRepository){
    suspend fun execute(id: String, title: String) = getFavor(id, title)
    suspend operator fun invoke(id: String, title: String) = getFavor(id, title)
    private suspend fun getFavor(id: String, title: String) = repository.getFavor(id, title)
}