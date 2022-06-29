package com.application.ebcom.feature.message.domain.use_case

class MessageUseCases(
    val addMessage: AddMessage,
    val addAllMessage: AddAllMessage,
    val deleteMessage: DeleteMessage,
    val deleteMessages: DeleteMessages,
    val saveMessage: SaveMessage,
    val readMessage: ReadMessage,
    val getAllMessageLocal: GetAllMessageLocal,
    val getAllMessageRemote: GetAllMessageRemote,
    val unSaveMessage: UnSaveMessage,
)