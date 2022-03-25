package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavoriteUser() = userRepository.getFavoriteUsers()

    fun addUserToFavorite(userEntity: UserEntity) {
        userRepository.setFavoriteUser(userEntity, true)
    }

    fun deleteUserFromFavorite(userEntity: UserEntity) {
        userRepository.setFavoriteUser(userEntity, false)
    }
}