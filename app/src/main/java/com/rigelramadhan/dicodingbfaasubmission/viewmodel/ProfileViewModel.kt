package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserProfileEntity
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserProfileRepository

class ProfileViewModel(private val userProfileRepository: UserProfileRepository, private val login: String): ViewModel() {

    fun getProfile() = userProfileRepository.getProfile(login)

    fun getProfileRepos(user: UserProfileEntity) = userProfileRepository.getRepos(user)

    fun getProfileFollowings(user: UserProfileEntity) = userProfileRepository.getFollowings(user)

    fun getProfileFollowers(user: UserProfileEntity) = userProfileRepository.getFollowers(user)

    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }
}