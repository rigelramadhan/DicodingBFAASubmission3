package com.rigelramadhan.dicodingbfaasubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserProfileEntity

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM profiles WHERE login = :login")
    fun getProfile(login: String): LiveData<UserProfileEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProfile(user: UserProfileEntity)

    @Query("DELETE FROM profiles")
    fun deleteAllProfiles()

    @Query("DELETE FROM profiles WHERE login = :login")
    fun deleteProfile(login: String)

    @Query("SELECT EXISTS(SELECT * FROM profiles WHERE login = :login)")
    fun isProfileExists(login: String): Boolean
}