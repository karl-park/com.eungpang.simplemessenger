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
}

class ChatRepositoryImpl: ChatRepository {
    override suspend fun retrieveChatHistory(
        roomId: String,
        page: Int,
        limit: Int
    ): List<Message> {
        TODO("Not yet implemented")
    }
}

class MockChatRepositoryImpl(
    private val applicationContext: Context
): ChatRepository {
    override suspend fun retrieveChatHistory(
        roomId: String,
        page: Int,
        limit: Int
    ): List<Message> {
        val pref = applicationContext.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        val userId = pref.getString(ConstantPref.KEY_USER_ID, null)!!

        return listOf(
            Message(
                userId,
                roomId,
                "Hi, Nice to meet you.",
                Date(1634225659469L),
                null,
                true
            ),
            Message(
                userId,
                roomId,
                "Excuse me, can you hear me?",
                Date(1634239659469L),
                null,
                true
            ),
            Message(
                userId,
                roomId,
                "Have a nice day!",
                Date(1634249659469L),
                null,
                true
            ),
            Message(
                "${userId}-echobot-1",
                roomId,
                "Good to see you.",
                Date(1634249659469L),
                null,
                true
            )
        )
    }
}