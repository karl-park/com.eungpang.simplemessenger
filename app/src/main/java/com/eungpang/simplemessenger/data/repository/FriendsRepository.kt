package com.eungpang.simplemessenger.data.repository

import android.app.Application
import android.content.Context
import com.eungpang.simplemessenger.domain.bot.BotType
import com.eungpang.simplemessenger.domain.friend.Profile
import com.eungpang.simplemessenger.shared.ConstantPref
import java.util.*

interface FriendsRepository {
    suspend fun findFriends(userId: String) : List<Profile>
    suspend fun getUserProfile(userId: String) : Profile?
}

class MockFriendsRepositoryImpl(
    private val applicationContext: Context
): FriendsRepository {
    private val userId: String
    init {
        val pref = applicationContext.getSharedPreferences(ConstantPref.KEY_PREF_NAME, Application.MODE_PRIVATE)
        userId = pref.getString(ConstantPref.KEY_USER_ID, null)!!
    }

    private val mockDatabase = mutableMapOf(
        userId to Profile(
            userId,
            "Karl",
            null,
            null,
            "karl.panki.park@gmail.com",
            Date(1634281435338),
            null
        ),
        "${userId}-echobot-1" to Profile(
            "${userId}-echobot-1",
            "Jone",
            "https://www.jenkins.io/images/logos/snow/snow.png",
            null,
            "echobot1@eungpang.com",
            Date(1634225659469L),
            BotType.EchoBot
        ),
        "${userId}-echobot-2" to Profile(
            "${userId}-echobot-2",
            "Kent",
            "https://www.jenkins.io/images/logos/cute/cute.png",
            null,
            "echobot2@eungpang.com",
            Date(1632025656469L),
            BotType.EchoBot
        )
    )

    override suspend fun findFriends(userId: String): List<Profile> {
        return mockDatabase.filter {
            it.value.botType != null
        }.values.toList()
    }

    override suspend fun getUserProfile(userId: String): Profile? {
        return mockDatabase[userId]
    }
}

