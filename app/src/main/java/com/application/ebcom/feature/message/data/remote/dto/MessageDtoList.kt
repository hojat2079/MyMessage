package com.application.ebcom.feature.message.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MessageDtoList(
    @SerializedName("messages")
    val messages: List<MessageDto>
)