package com.application.ebcom.feature.message.domain.use_case

import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.repository.MessageRepository
import com.application.ebcom.util.InvalidMessageException
import kotlin.jvm.Throws

class AddMessage(
    private val repository: MessageRepository
) {
    @Throws(InvalidMessageException::class)
    suspend operator fun invoke(
        message: Message
    ) {
        if (message.title.isBlank()) {
            throw  InvalidMessageException(
                message = "The title of the note cannot be empty"
            )
        }
        if (message.description.isBlank()) {
            throw  InvalidMessageException(
                message = "The description of the note cannot be empty"
            )
        }
        repository.insertNewMessage(messageEntity = message.toMessageEntity() )
    }
}