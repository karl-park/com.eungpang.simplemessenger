package com.eungpang.simplemessenger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eungpang.simplemessenger.data.mapper.DomainMapper
import com.eungpang.simplemessenger.domain.bot.BotType
import com.eungpang.simplemessenger.domain.chat.Message
import com.eungpang.simplemessenger.domain.friend.Profile
import java.util.*

@Entity(tableName = "tb_profile")
data class ProfileEntity(
    @PrimaryKey
    val userId: String,
    val name: String,
    val avatarUrl: String?,
    val status: String?,
    val email: String?,
    val createdDate: Date,
    val botType: BotType?
) {
    companion object : DomainMapper<ProfileEntity, Profile> {
        override fun mapTo(from: ProfileEntity): Profile =
            Profile(
                userId = from.userId,
                name = from.name,
                avatarUrl = from.avatarUrl,
                status = from.status,
                email = from.email,
                createdDate = from.createdDate,
                botType = from.botType
            )

        override fun mapFrom(from: Profile): ProfileEntity =
            ProfileEntity(
                userId = from.userId,
                name = from.name,
                avatarUrl = from.avatarUrl,
                status = from.status,
                email = from.email,
                createdDate = from.createdDate,
                botType = from.botType
            )
    }
}