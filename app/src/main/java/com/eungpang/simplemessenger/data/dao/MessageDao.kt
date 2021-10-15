package com.eungpang.simplemessenger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eungpang.simplemessenger.data.entity.MessageEntity

@Dao
interface MessageDao {
    @Query("SELECT * FROM tb_message WHERE roomId = :roomId")
    fun getAllMessages(roomId: String): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: MessageEntity)
}
