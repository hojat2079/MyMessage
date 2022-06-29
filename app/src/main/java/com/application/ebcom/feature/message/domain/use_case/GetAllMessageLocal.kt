package com.application.ebcom.feature.message.domain.use_case

import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class GetAllMessageLocal(
    private val repository: MessageRepository
) {
    operator fun invoke(
    ): Flow<List<Message>> {
        return repository.getAllSavedMessage().map { messages ->

            Timber.e("$messages")
            messages.map { it.toMessage() }
        }
    }
}