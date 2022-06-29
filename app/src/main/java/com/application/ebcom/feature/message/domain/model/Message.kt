package com.application.ebcom.feature.message.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.ebcom.feature.message.data.local.entity.MessageEntity

data class Message(
    val description: String,
    var id: Int? = null,
    val image: String?,
    val title: String,
    var unread: Boolean,
    var expand: ExpandItemState,
    var saved:Boolean,
    var selected:SelectItemState
){
    fun toMessageEntity():MessageEntity{
        return MessageEntity(
            description=description,
            title = title,
            unread = unread,
            id = id,
            image = image ?: "",
            saved = saved
        )
    }
}