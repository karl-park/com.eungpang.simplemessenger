package com.eungpang.simplemessenger.domain.friend

import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetUserProfileUseCase {
    suspend fun invoke(userId: String): Result<Profile>

    class UserNotFoundException: Throwable()
}

class GetUserProfileUseCaseImpl @Inject constructor(
    private val repo: FriendsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetUserProfileUseCase {
    override suspend fun invoke(userId: String): Result<Profile> =
        try {
            withContext(dispatcher) {
                val profile = repo.getUserProfile(userId)
                if (profile != null) {
                    Result.Success(profile)
                } else {
                    Result.Error(
                        GetUserProfileUseCase.UserNotFoundException()
                    )
                }

            }
        } catch (e: Exception) {
            Result.Error(e)
        }
}
