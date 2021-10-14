package com.eungpang.simplemessenger.domain.di

import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCase
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCaseImpl
import com.eungpang.simplemessenger.shared.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Provides
    fun provideGetFriendsListUseCase(
        repository: FriendsRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetFriendsListUseCase {
        // TODO: For testing,
        //  It's better to have multiple buildTypes
        return GetFriendsListUseCaseImpl(repository, dispatcher)
    }
}
