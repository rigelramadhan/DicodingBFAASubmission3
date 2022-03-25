package com.rigelramadhan.dicodingbfaasubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUser(login: String): UserEntity

    @Query("SELECT * FROM users WHERE isFavorite = 1")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login)")
    fun isUserExists(login: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: List<UserEntity>)

    @Update
    fun userFavorite(user: UserEntity)

    @Query("UPDATE users SET isFavorite = :favoriteState WHERE login = :login")
    fun updateUserFavorite(login: String, favoriteState: Boolean)

    @Query("DELETE FROM users WHERE isFavorite = 0")
    fun deleteAllUsers()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE login = :login AND isFavorite = 1)")
    fun isUserFavorite(login: String): Boolean
}