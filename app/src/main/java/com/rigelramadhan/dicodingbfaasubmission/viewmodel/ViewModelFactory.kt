package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserRepository
import com.rigelramadhan.dicodingbfaasubmission.di.Injection

class ViewModelFactory private constructor(
    private val userRepository: UserRepository
    ) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Wrong ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideUserRepository(context))
            }.also { instance = it }
    }
}