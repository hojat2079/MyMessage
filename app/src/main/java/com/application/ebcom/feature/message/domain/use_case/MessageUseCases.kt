package com.application.ebcom.feature.message.domain.use_case

class MessageUseCases(
    val addMessage: AddMessage,
    val deleteMessage: DeleteMessage,
    val readMessage: ReadMessage,
    val getAllMessageLocal: GetAllMessageLocal,
    val getAllMessageRemote: GetAllMessageRemote
)