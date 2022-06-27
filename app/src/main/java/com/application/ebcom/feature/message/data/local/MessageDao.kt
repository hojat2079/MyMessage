package com.application.ebcom.feature.message.data.local

import androidx.room.*
import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMessage(messageEntity: MessageEntity)

    @Query("DELETE FROM MESSAGES WHERE ID =:id")
    suspend fun deleteMessage(id: Int)

    @Query("UPDATE MESSAGES SET UNREAD = 1 WHERE ID = :id ")
    suspend fun readMessage(id: Int)

    @Query("SELECT * FROM MESSAGES")
    fun getAllSavedMessage(): Flow<List<MessageEntity>>
}