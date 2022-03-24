package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserProfileRepository
import com.rigelramadhan.dicodingbfaasubmission.di.Injection

class ProfileViewModelFactory private constructor(
    private val userProfileRepository: UserProfileRepository,
    private val login: String
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userProfileRepository, login) as T
        }

        throw IllegalArgumentException("Wrong ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ProfileViewModelFactory? = null

        fun getInstance(context: Context, login: String): ProfileViewModelFactory {
            if (instance != null) {
                if (instance?.login != login) {
                    synchronized(this) {
                        return ProfileViewModelFactory(Injection.provideProfileRepository(context), login)
                    }
                }
                return instance as ProfileViewModelFactory
            } else {
                synchronized(this) {
                    return instance ?: ProfileViewModelFactory(Injection.provideProfileRepository(context), login)
                }
            }
        }
    }
}