package com.eungpang.simplemessenger.data.repository

import com.eungpang.simplemessenger.data.database.SimpleMessengerDatabase
import com.eungpang.simplemessenger.data.entity.ProfileEntity
import com.eungpang.simplemessenger.domain.friend.Profile

interface FriendsRepository {
    suspend fun findFriends(userId: String) : List<Profile>
    suspend fun getUserProfile(userId: String) : Profile?
    suspend fun addUserProfile(profile: Profile) : Profile?
    suspend fun addUserProfiles(profiles: List<Profile>) : List<Profile>?
}

class FriendsRepositoryImpl(
    private val database: SimpleMessengerDatabase
) : FriendsRepository {
    override suspend fun findFriends(userId: String): List<Profile> {
        return database.profileDao().getAllFriendList(userId).map {
            ProfileEntity.mapTo(it)
        }
    }

    override suspend fun getUserProfile(userId: String): Profile? {
        return database.profileDao().getProfile(userId).firstOrNull()?.run {
            ProfileEntity.mapTo(this)
        }
    }

    override suspend fun addUserProfile(profile: Profile): Profile? {
        database.profileDao().addProfile(ProfileEntity.mapFrom(profile))
        return profile
    }

    override suspend fun addUserProfiles(profiles: List<Profile>): List<Profile>? {
        database.profileDao().addProfiles(profiles.map { ProfileEntity.mapFrom(it) })
        return profiles
    }
}
