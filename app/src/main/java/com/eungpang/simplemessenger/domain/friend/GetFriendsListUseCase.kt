package com.eungpang.simplemessenger.domain.friend

import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetFriendsListUseCase {
    suspend fun invoke(userId: String): Result<List<Profile>>
}

class GetFriendsListUseCaseImpl @Inject constructor(
    private val repo: FriendsRepository,
    private val dispatcher: CoroutineDispatcher
) : GetFriendsListUseCase {
    override suspend fun invoke(userId: String): Result<List<Profile>> =
        try {
            withContext(dispatcher) {
                Result.Success(repo.findFriends(userId))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
}
