package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import androidx.lifecycle.ViewModel
import com.rigelramadhan.dicodingbfaasubmission.data.Result
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserProfileEntity
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserProfileRepository

class ProfileViewModel(private val userProfileRepository: UserProfileRepository, private val login: String): ViewModel() {

    fun getProfile() = userProfileRepository.getProfile(login)

    fun getProfileRepos() = userProfileRepository.getRepos()

    fun getProfileFollowings() = userProfileRepository.getFollowings()

    fun getProfileFollowers() = userProfileRepository.getFollowers()

    companion object {
        private val TAG = ProfileViewModel::class.java.simpleName
    }
}