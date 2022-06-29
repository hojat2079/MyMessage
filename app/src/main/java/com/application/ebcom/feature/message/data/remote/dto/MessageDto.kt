package com.application.ebcom.feature.message.data.remote.dto


import com.application.ebcom.feature.message.data.local.entity.MessageEntity
import com.application.ebcom.feature.message.domain.model.ExpandItemState
import com.application.ebcom.feature.message.domain.model.Message
import com.application.ebcom.feature.message.domain.model.SelectItemState
import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("unread")
    val unread: Boolean
) {
    fun toMessage() = Message(
        description = description,
        image = image,
        title = title,
        unread = unread,
        id = id.toInt(),
        expand = ExpandItemState.UNEXPANDED,
        saved = false,
        selected = SelectItemState.GONE
    )
}