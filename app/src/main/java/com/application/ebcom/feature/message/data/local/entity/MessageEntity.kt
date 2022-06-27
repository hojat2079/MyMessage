package com.application.ebcom.feature.message.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.ebcom.feature.message.domain.model.Message

@Entity(tableName = "messages")
data class MessageEntity(
    val description: String,
    @PrimaryKey var id: Int? = null,
    val image: String="",
    val title: String,
    val unread: Boolean
){
    fun toMessage() = Message(
        description=description,
        image = image,
        title = title,
        unread = unread,
        id = id,
        expand = false,
    )
}