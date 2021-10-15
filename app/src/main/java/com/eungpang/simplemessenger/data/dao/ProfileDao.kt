package com.eungpang.simplemessenger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eungpang.simplemessenger.data.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Query("SELECT * FROM tb_profile WHERE userId != :myId")
    fun getAllFriendList(myId: String): List<ProfileEntity>

    @Query("SELECT * FROM tb_profile WHERE userId = :userId")
    fun getProfile(userId: String): List<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProfile(profiles: ProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProfiles(profiles: List<ProfileEntity>)
}
