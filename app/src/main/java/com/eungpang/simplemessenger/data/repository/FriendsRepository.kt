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
                "https://www.jenkins.io/images/logos/snow/snow.png",
                null,
                "echobot1@eungpang.com",
                Date(1634225659469L),
                BotType.EchoBot
            ),
            Profile(
                "echobot-2",
                "Kent",
                "https://www.jenkins.io/images/logos/cute/cute.png",
                null,
                "echobot2@eungpang.com",
                Date(1632025656469L),
                BotType.EchoBot
            )
        )
    }
}

