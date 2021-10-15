package com.eungpang.simplemessenger.domain.chat

import com.eungpang.simplemessenger.data.repository.ChatRepository
import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetChatHistoryUseCase {
    suspend fun invoke(
        roomId: String,
        page: Int,
        limit: Int
    ): Result<List<Message>>
}

class GetChatHistoryUseCaseImpl @Inject constructor(
    private val repo: ChatRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetChatHistoryUseCase {

    override suspend fun invoke(roomId: String, page: Int, limit: Int): Result<List<Message>> =
        try {
            withContext(dispatcher) {
                Result.Success(repo.retrieveChatHistory(roomId, page, limit))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
}
