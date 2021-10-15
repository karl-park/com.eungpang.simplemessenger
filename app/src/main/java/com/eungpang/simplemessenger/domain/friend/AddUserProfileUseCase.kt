package com.eungpang.simplemessenger.domain.friend

import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.common.Result
import com.eungpang.simplemessenger.shared.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddUserProfileUseCase {
    suspend fun invoke(profile: Profile): Result<Profile>
    suspend fun invoke(profiles: List<Profile>): Result<List<Profile>>
}

class AddUserProfileUseCaseImpl @Inject constructor(
    private val repo: FriendsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AddUserProfileUseCase {
    override suspend fun invoke(profile: Profile): Result<Profile> =
        try {
            withContext(dispatcher) {
                repo.addUserProfile(profile)
                Result.Success(profile)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun invoke(profiles: List<Profile>): Result<List<Profile>> =
        try {
            withContext(dispatcher) {
                repo.addUserProfiles(profiles)
                Result.Success(profiles)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
}
