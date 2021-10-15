package com.eungpang.simplemessenger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eungpang.simplemessenger.data.converter.RoomConverter
import com.eungpang.simplemessenger.data.dao.MessageDao
import com.eungpang.simplemessenger.data.dao.ProfileDao
import com.eungpang.simplemessenger.data.entity.MessageEntity
import com.eungpang.simplemessenger.data.entity.ProfileEntity

@Database(
    entities = [
        ProfileEntity::class,
        MessageEntity::class
    ],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class SimpleMessengerDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun messageDao(): MessageDao
}