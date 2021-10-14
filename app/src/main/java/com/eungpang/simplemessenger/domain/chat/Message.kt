package com.eungpang.simplemessenger.domain.chat

import java.util.*

data class Message(
    val userId: String,
    val message: String,
    val avatarUrl: String? = null,
    val imageUrl: String? = null,
    val createdDate: Date,
    val hasSent: Boolean
)
