package com.rigelramadhan.dicodingbfaasubmission.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.FavoriteUserEntity
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.FavoriteUserDao
import com.rigelramadhan.dicodingbfaasubmission.data.local.room.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllUsers(): LiveData<List<FavoriteUserEntity>> = mFavoriteUserDao.getAllUsers()

    fun insert(favoriteUser: FavoriteUserEntity) {
        executorService.execute { mFavoriteUserDao.insert(favoriteUser) }
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        executorService.execute { mFavoriteUserDao.delete(favoriteUser) }
    }
}