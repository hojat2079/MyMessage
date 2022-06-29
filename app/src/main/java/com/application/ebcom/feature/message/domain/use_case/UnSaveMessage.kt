package com.application.ebcom.feature.message.domain.use_case

import com.application.ebcom.feature.message.domain.repository.MessageRepository

class UnSaveMessage(
    private val repository: MessageRepository

) {
    suspend operator fun invoke(id: Int) {
        return repository.unSaveMessage(id)
    }
}