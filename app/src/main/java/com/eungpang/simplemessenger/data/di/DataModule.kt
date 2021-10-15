package com.eungpang.simplemessenger.data.di

import android.content.Context
import androidx.room.Room
import com.eungpang.simplemessenger.data.database.SimpleMessengerDatabase
import com.eungpang.simplemessenger.data.repository.ChatRepository
import com.eungpang.simplemessenger.data.repository.ChatRepositoryImpl
import com.eungpang.simplemessenger.data.repository.FriendsRepository
import com.eungpang.simplemessenger.data.repository.FriendsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    private const val DATABASE = "db_simple_messenger"

    @Provides
    @Singleton
    fun providesSimpleMessengerDatabase(
        @ApplicationContext applicationContext: Context
    ): SimpleMessengerDatabase {
        return Room.databaseBuilder(
            applicationContext,
            SimpleMessengerDatabase::class.java, DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun providesFriendsRepository(
        database: SimpleMessengerDatabase
    ): FriendsRepository {
        return FriendsRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun providesChatRepository(
        database: SimpleMessengerDatabase
    ): ChatRepository {
        return ChatRepositoryImpl(database)
    }
}
