package com.eungpang.simplemessenger.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eungpang.simplemessenger.data.mapper.DomainMapper
import com.eungpang.simplemessenger.domain.chat.Message
import java.util.*

@Entity(tableName = "tb_message")
data class MessageEntity(
    val authorId: String,
    val roomId: String,
    val message: String,
    val createdDate: Date = Date(),
    val imageUrl: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var _messageId: Int? = null

    companion object : DomainMapper<MessageEntity, Message> {
        override fun mapTo(from: MessageEntity): Message =
            Message(
                authorId = from.authorId,
                friendId = Message.parseUsersFromRoomId(from.roomId)?.second ?: "",
                message = from.message,
                createdDate = from.createdDate,
                imageUrl = from.imageUrl,
            )

        override fun mapFrom(from: Message): MessageEntity =
            MessageEntity(
                authorId = from.authorId,
                roomId = Message.retrieveRoomId(from.authorId, from.friendId),
                message = from.message,
                createdDate = from.createdDate,
                imageUrl = from.imageUrl,
            )
    }
}