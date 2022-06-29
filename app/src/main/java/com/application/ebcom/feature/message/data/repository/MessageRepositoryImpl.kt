package com.application.ebcom.feature.message.data.repository

import com.application.ebcom.feature.message.data.local.MessageDao
import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import com.application.ebcom.feature.message.data.remote.MessageApiService
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.repository.MessageRepository
import com.application.ebcom.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class MessageRepositoryImpl(
    private val api: MessageApiService,
    private val dao: MessageDao
) : MessageRepository {

    override fun getInformationData(): Flow<Resource<List<Message>>> = flow {
        Timber.e("Loading infos")
        emit(Resource.Loading())
        try {
            val infos =
                api.getInformationData().messages.sortedBy { !it.unread }
            Timber.e("$infos")

            emit(Resource.Success(data = infos.map {
                it.toMessage()
            }))

        } catch (ex: HttpException) {
            Timber.e("Error infos ${ex.message()}")
            emit(
                Resource.Error(
                    message = "Couldn't reach server, please check network connection.",
                )
            )

        } catch (ex: IOException) {
            Timber.e("Error infos ${ex}")
            emit(
                Resource.Error(
                    message = "oops, something went wrong",
                )
            )
        }
    }


    override suspend fun deleteMessage(id: Int) {
        return dao.deleteMessage(id)
    }

    override suspend fun deleteMessages(ids: List<Int>) {
        return dao.deleteMessages(ids)
    }

    override suspend fun readMessage(id: Int) {
        return dao.readMessage(id)
    }

    override suspend fun saveMessage(id: Int) {
        return dao.saveMessage(id)
    }

    override suspend fun unSaveMessage(id: Int) {
        return dao.unSaveMessage(id)
    }

    override suspend fun insertNewMessage(messageEntity: MessageEntity) {
        return dao.insertNewMessage(messageEntity)
    }

    override suspend fun insertAllMessage(list: List<MessageEntity>) {
        return dao.insertAllMessage(list)
    }

    override fun getAllSavedMessage(): Flow<List<MessageEntity>> {
        return dao.getAllSavedMessage()

    }
}