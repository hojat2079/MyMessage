package com.application.ebcom.feature.message.domain.use_case

import com.application.ebcom.feature.message.domain.repository.MessageRepository

class DeleteMessages(
    private val repository: MessageRepository
) {
    suspend operator fun invoke(ids: List<Int>) {
        return repository.deleteMessages(ids)
    }
}
