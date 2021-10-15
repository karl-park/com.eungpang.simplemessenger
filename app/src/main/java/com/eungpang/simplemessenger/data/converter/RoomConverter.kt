package com.eungpang.simplemessenger.data.converter

import androidx.room.TypeConverter
import com.eungpang.simplemessenger.domain.bot.BotType
import java.util.*

class RoomConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBotTypeInt(value: Int): BotType? {
        return try { BotType.values()[value] } catch (e: Exception) { null }
    }

    @TypeConverter
    fun botTypeToBotTypeString(botType: BotType?): Int {
        if (botType == null) return -1
        return botType.ordinal
    }
}
