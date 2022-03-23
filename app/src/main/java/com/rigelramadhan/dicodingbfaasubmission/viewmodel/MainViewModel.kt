package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserRepository
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers(query: String = "a") = userRepository.getUsers(query)

    fun getFavoriteUsers() = userRepository.getFavoriteUsers()

    fun isDataEmpty() = userRepository.isDataEmpty()

    fun addUserToFavorite(userEntity: UserEntity) {
        userRepository.setFavoriteUser(userEntity, true)
    }

    fun deleteUserFromFavorite(userEntity: UserEntity) {
        userRepository.setFavoriteUser(userEntity, false)
    }
}