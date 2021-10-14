package com.eungpang.simplemessenger.domain.friend

import com.eungpang.simplemessenger.domain.bot.BotType
import java.util.*

data class Profile(
    val userId: String,
    val name: String,
    val avatarUrl: String?,
    val status: String?,
    val email: String?,
    val createdDate: Date,
    val botType: BotType? = null
)
