package com.eungpang.simplemessenger.data.repository

import com.eungpang.simplemessenger.domain.bot.BotType
import com.eungpang.simplemessenger.domain.friend.Profile
import java.util.*

interface FriendsRepository {
    suspend fun findFriends(userId: String) : List<Profile>
}

class FriendsRepositoryImpl: FriendsRepository {
    override suspend fun findFriends(userId: String): List<Profile> {
        TODO("Not yet implemented")
    }
}

class MockFriendsRepositoryImpl: FriendsRepository {
    override suspend fun findFriends(userId: String): List<Profile> {
        return listOf(
            Profile(
                "echobot-1",
                "Jone",
                null,
                "echobot1@eungpang.com",
                Date(1634225659469L),
                BotType.EchoBot
            ),
            Profile(
                "echobot-2",
                "Kent",
                null,
                "echobot2@eungpang.com",
                Date(1634225659469L),
                BotType.EchoBot
            )
        )
    }
}

