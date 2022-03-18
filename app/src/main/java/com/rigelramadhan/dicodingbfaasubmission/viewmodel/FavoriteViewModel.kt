package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.FavoriteUserEntity
import com.rigelramadhan.dicodingbfaasubmission.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllUsers(): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteUserRepository.getAllUsers()
    }

    fun insert(favoriteUser: FavoriteUserEntity) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUserEntity) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
}