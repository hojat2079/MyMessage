package com.application.ebcom.feature.message.domain.use_case

import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.repository.MessageRepository
import com.application.ebcom.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class GetAllMessageRemote(
    private  val repository: MessageRepository
) {
    operator fun invoke(
    ): Flow<Resource<List<Message>>> {
        return repository.getInformationData()
    }
}