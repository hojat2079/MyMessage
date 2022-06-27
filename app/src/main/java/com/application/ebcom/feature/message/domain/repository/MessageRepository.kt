package com.application.ebcom.feature.message.domain.repository

import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.util.Resource
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getInformationData(): Flow<Resource<List<Message>>>

    suspend fun deleteMessage(id: Int)

    suspend fun readMessage(id: Int)

    suspend fun insertNewMessage(messageEntity: MessageEntity)

    fun getAllSavedMessage(): Flow<List<MessageEntity>>


}