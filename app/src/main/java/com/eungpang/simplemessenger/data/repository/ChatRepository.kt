package com.eungpang.simplemessenger.data.repository

import android.app.Application
import android.content.Context
import com.eungpang.simplemessenger.domain.chat.Message
import com.eungpang.simplemessenger.shared.ConstantPref
import java.util.*

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

class MockChatRepositoryImpl(
    private val applicationContext: Context
): ChatRepository {
    private val userId: String
    init {
        val pref = applicationContext.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        userId = pref.getString(ConstantPref.KEY_USER_ID, null)!!
    }

    private val chatRepository = mutableMapOf<String, MutableList<Message>>().apply {
        val bot1 = "${userId}-echobot-1"
        val bot2 = "${userId}-echobot-2"
        val bot1Room = listOf(userId, bot1).sorted().joinToString("||")
        val bot2Room = listOf(userId, bot2).sorted().joinToString("||")

        this[bot1Room] = mutableListOf(
            Message(
                userId,
                bot1Room,
                "Hi, Nice to meet you.",
                Date(1634225659469L),
                null,
            ),
            Message(
                userId,
                bot1Room,
                "Excuse me, can you hear me?",
                Date(1634239659469L),
                null,
            ),
            Message(
                userId,
                bot1Room,
                "Have a nice day!",
                Date(1634249659469L),
                null,
            ),
            Message(
                "${userId}-echobot-1",
                bot1Room,
                "Good to see you.",
                Date(1634249659469L),
                null,
            )
        )

        this[bot2Room] = mutableListOf(
            Message(
                userId,
                bot2Room,
                "Hi, Kent! How are you?",
                Date(1634225659469L),
                null,
            ),
            Message(
                userId,
                bot2Room,
                "It's been a while.",
                Date(1634249659469L),
                null,
            ),
            Message(
                "${userId}-echobot-2",
                bot2Room,
                "Have a nice day man!",
                Date(1634249659469L),
                null,
            )
        )
    }

    override suspend fun retrieveChatHistory(
        roomId: String,
        page: Int,
        limit: Int
    ): List<Message> {
        return chatRepository[roomId] ?: listOf()
    }

    override suspend fun sendMessage(message: Message): Boolean {
        chatRepository[message.roomId]?.add(message)
        return true
    }
}