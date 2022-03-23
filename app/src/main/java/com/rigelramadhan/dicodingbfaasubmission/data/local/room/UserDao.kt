package com.rigelramadhan.dicodingbfaasubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE isFavorite = 1")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: List<UserEntity>)

    @Update
    fun updateUsers(user: UserEntity)

    @Query("DELETE FROM users WHERE isFavorite = 0")
    fun deleteAllUsers()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login AND isFavorite = 1)")
    fun isUserFavorite(login: String): Boolean
}