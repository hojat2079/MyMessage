package com.application.ebcom.feature.message.domain.use_case

import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import com.application.ebcom.feature.message.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllMessageLocal(
    private val repository: MessageRepository
) {
    operator fun invoke(
    ): Flow<List<MessageEntity>> {
        return repository.getAllSavedMessage().map { messages ->
            messages.sortedBy { !it.unread }.sortedBy { it.title.lowercase() }
        }
    }
}