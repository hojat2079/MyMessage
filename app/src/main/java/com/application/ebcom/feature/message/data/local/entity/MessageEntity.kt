package com.application.ebcom.feature.message.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.ebcom.feature.message.domain.model.ExpandItemState
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.model.SelectItemState

@Entity(tableName = "messages")
data class MessageEntity(
    val description: String,
    @PrimaryKey var id: Int? = null,
    val image: String = "",
    val title: String,
    val unread: Boolean,
    val saved:Boolean
) {
    fun toMessage() = Message(
        description = description,
        image = image,
        title = title,
        unread = unread,
        id = id,
        expand = ExpandItemState.UNEXPANDED,
        saved = saved,
        selected = SelectItemState.GONE
    )
}