package com.rigelramadhan.dicodingbfaasubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert
    fun insert(user: FavoriteUserEntity)

    @Delete
    fun delete(user: FavoriteUserEntity)

    @Query("SELECT * FROM favoriteuserentity ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<FavoriteUserEntity>>
}