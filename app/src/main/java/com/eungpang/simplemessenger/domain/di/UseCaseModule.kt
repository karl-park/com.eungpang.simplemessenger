package com.eungpang.simplemessenger.domain.di

import com.eungpang.simplemessenger.data.repository.ChatRepository
import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.chat.GetChatHistoryUseCase
import com.eungpang.simplemessenger.domain.chat.GetChatHistoryUseCaseImpl
import com.eungpang.simplemessenger.domain.chat.SendMessageUseCase
import com.eungpang.simplemessenger.domain.chat.SendMessageUseCaseImpl
import com.eungpang.simplemessenger.domain.friend.*
import com.eungpang.simplemessenger.shared.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Provides
    fun provideGetFriendsListUseCase(
        repository: FriendsRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetFriendsListUseCase {
        return GetFriendsListUseCaseImpl(repository, dispatcher)
    }

    @Provides
    fun provideAddUserProfileUseCase(
        repository: FriendsRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): AddUserProfileUseCase {
        return AddUserProfileUseCaseImpl(repository, dispatcher)
    }

    @Provides
    fun provideGetUserProfileUseCase(
        repository: FriendsRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetUserProfileUseCase {
        return GetUserProfileUseCaseImpl(repository, dispatcher)
    }

    @Provides
    fun provideGetChatHistoryUseCase(
        repository: ChatRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetChatHistoryUseCase {
        return GetChatHistoryUseCaseImpl(repository, dispatcher)
    }

    @Provides
    fun provideSendMessageUseCase(
        repository: ChatRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): SendMessageUseCase {
        return SendMessageUseCaseImpl(repository, dispatcher)
    }
}
