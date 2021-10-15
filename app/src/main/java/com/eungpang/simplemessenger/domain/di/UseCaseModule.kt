package com.eungpang.simplemessenger.domain.di

import com.eungpang.simplemessenger.data.repository.ChatRepository
import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.domain.chat.GetChatHistoryUseCase
import com.eungpang.simplemessenger.domain.chat.GetChatHistoryUseCaseImpl
import com.eungpang.simplemessenger.domain.chat.SendMessageUseCase
import com.eungpang.simplemessenger.domain.chat.SendMessageUseCaseImpl
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCase
import com.eungpang.simplemessenger.domain.friend.GetFriendsListUseCaseImpl
import com.eungpang.simplemessenger.domain.friend.GetUserProfileUseCase
import com.eungpang.simplemessenger.domain.friend.GetUserProfileUseCaseImpl
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

    @Provides
    fun provideGetChatHistoryUseCase(
        repository: ChatRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetChatHistoryUseCase {
        // TODO: For testing,
        //  It's better to have multiple buildTypes
        return GetChatHistoryUseCaseImpl(repository, dispatcher)
    }

    @Provides
    fun provideGetUserProfileUseCase(
        repository: FriendsRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetUserProfileUseCase {
        // TODO: For testing,
        //  It's better to have multiple buildTypes
        return GetUserProfileUseCaseImpl(repository, dispatcher)
    }

    @Provides
    fun provideSendMessageUseCase(
        repository: ChatRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): SendMessageUseCase {
        // TODO: For testing,
        //  It's better to have multiple buildTypes
        return SendMessageUseCaseImpl(repository, dispatcher)
    }
}
