package com.eungpang.simplemessenger.domain.chat

import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val authorId: String,
    val roomId: String,
    val message: String,
    val createdDate: Date = Date(),
    val imageUrl: String? = null
) {
    companion object {
        const val TIME_FORMAT = "hh:mm a"
        const val DATE_FORMAT = "dd MMM yy"
    }

    fun dateString() : String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val todayDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val todayYear = calendar.get(Calendar.YEAR)

        calendar.time = createdDate
        val createdDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val createdYear = calendar.get(Calendar.YEAR)

        return if (todayYear == createdYear &&
            todayDayOfYear == createdDayOfYear) {
            // use time format
            val format = SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH)
            format.format(createdDate)
        } else if (todayYear == createdYear &&
            todayDayOfYear - 1 == createdDayOfYear) {
            // yesterday
            "Yesterday"
        } else if (todayYear == createdYear &&
            todayDayOfYear - 2 == createdDayOfYear) {
            "2 days ago"
        } else {
            // use date format
            val format = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
            format.format(createdDate)
        }
    }
}
