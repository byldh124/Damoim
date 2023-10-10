package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class SetFavorUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend fun execute(id: String, title: String, active: Boolean) = repository.setFavor(id, title, active)
    suspend operator fun invoke(id: String, title: String, active: Boolean) = repository.setFavor(id, title, active)
    private suspend fun setFavor(id: String, title: String, active: Boolean) = repository.setFavor(id, title, active)
}