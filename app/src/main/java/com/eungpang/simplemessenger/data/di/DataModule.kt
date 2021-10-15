package com.eungpang.simplemessenger.data.di

import android.content.Context
import com.eungpang.simplemessenger.data.repository.ChatRepository
import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.data.repository.MockChatRepositoryImpl
import com.eungpang.simplemessenger.data.repository.MockFriendsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    fun providesFriendsRepository(
        @ApplicationContext applicationContext: Context
    ): FriendsRepository {
        // TODO: For testing
        //  It's better to have multiple buildTypes
        return MockFriendsRepositoryImpl(applicationContext)
    }

    @Provides
    fun providesChatRepository(
        @ApplicationContext applicationContext: Context
    ): ChatRepository {
        // TODO: For testing
        //  It's better to have multiple buildTypes
        return MockChatRepositoryImpl(applicationContext)
    }
}
