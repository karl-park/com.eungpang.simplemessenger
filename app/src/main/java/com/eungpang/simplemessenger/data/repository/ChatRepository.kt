package com.eungpang.simplemessenger.data.repository

import com.eungpang.simplemessenger.data.database.SimpleMessengerDatabase
import com.eungpang.simplemessenger.data.entity.MessageEntity
import com.eungpang.simplemessenger.domain.chat.Message

interface ChatRepository {
    suspend fun retrieveChatHistory(
        roomId: String,
        page: Int = 0,
        limit: Int = 20
    ): List<Message>

    suspend fun sendMessage(
        message: Message
    ): Boolean
}

class ChatRepositoryImpl(
    private val database: SimpleMessengerDatabase
): ChatRepository {
    override suspend fun retrieveChatHistory(roomId: String, page: Int, limit: Int): List<Message> {
        return database.messageDao().getAllMessages(roomId).map { MessageEntity.mapTo(it) }
    }

    override suspend fun sendMessage(message: Message): Boolean {
        database.messageDao().saveMessage(MessageEntity.mapFrom(message))
        return true
    }
}
