package com.application.ebcom.feature.message.data.local

import androidx.room.*
import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMessage(messageEntity: MessageEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAllMessage(list: List<MessageEntity>)

    @Query("DELETE FROM MESSAGES WHERE ID =:id")
    suspend fun deleteMessage(id: Int)

    @Query("UPDATE MESSAGES SET saved = 1 WHERE ID = :id ")
    suspend fun saveMessage(id: Int)

    @Query("UPDATE MESSAGES SET saved = 0 WHERE ID = :id ")
    suspend fun unSaveMessage(id: Int)

    @Query("UPDATE MESSAGES SET unread = 0 WHERE ID = :id ")
    suspend fun readMessage(id: Int)

    @Query("SELECT * FROM MESSAGES")
    fun getAllSavedMessage(): Flow<List<MessageEntity>>

    @Query("DELETE FROM MESSAGES WHERE id in (:ids)")
    fun deleteMessages(ids:List<Int>)
}