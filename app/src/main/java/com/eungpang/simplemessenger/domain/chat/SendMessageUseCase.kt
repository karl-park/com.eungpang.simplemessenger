package com.eungpang.simplemessenger.domain.chat

import com.eungpang.simplemessenger.data.repository.ChatRepository
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SendMessageUseCase {
    suspend fun invoke(message: Message): Result<Unit>

    class SendMessageFailedException: Throwable()
}

class SendMessageUseCaseImpl @Inject constructor(
    private val repo: ChatRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SendMessageUseCase {

    override suspend fun invoke(message: Message): Result<Unit> =
        try {
            withContext(dispatcher) {
                // For mocking network delay
                delay(200L)

                val result  = repo.sendMessage(message)

                if (result) {
                    Result.Success(Unit)
                } else {
                    Result.Error(SendMessageUseCase.SendMessageFailedException())
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
}
