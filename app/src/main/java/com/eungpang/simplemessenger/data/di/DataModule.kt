package com.eungpang.simplemessenger.data.di

import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.data.repository.MockFriendsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    fun providesFriendsRepository(): FriendsRepository {
        // TODO: For testing
        //  It's better to have multiple buildTypes
        return MockFriendsRepositoryImpl()
    }
}
