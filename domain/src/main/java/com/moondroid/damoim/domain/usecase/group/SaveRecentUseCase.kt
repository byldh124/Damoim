package com.moondroid.damoim.domain.usecase.group

import com.moondroid.damoim.domain.repository.GroupRepository
import javax.inject.Inject

class SaveRecentUseCase @Inject constructor(private val repository: GroupRepository) {
    suspend fun execute(id: String, title: String, lastTime: String) = saveRecent(id, title, lastTime)

    suspend operator fun invoke(id: String, title: String, lastTime: String) = saveRecent(id, title, lastTime)

    private suspend fun saveRecent(id: String, title: String, lastTime: String) = repository.saveRecent(id, title, lastTime)
}