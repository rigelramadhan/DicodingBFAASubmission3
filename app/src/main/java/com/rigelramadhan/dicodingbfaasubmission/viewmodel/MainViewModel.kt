package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rigelramadhan.dicodingbfaasubmission.data.local.datastore.SettingPreferences
import com.rigelramadhan.dicodingbfaasubmission.data.repository.UserRepository
import com.rigelramadhan.dicodingbfaasubmission.data.local.entity.UserEntity

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getThemeSettings(dataStore: DataStore<Preferences>): LiveData<Boolean> {
        return SettingPreferences.getInstance(dataStore).getThemeSetting().asLiveData()
    }

    fun getUsers() = userRepository.getUsers()

    fun queryUsers(query: String) = userRepository.queryUser(query)

    fun insertUser(userEntity: UserEntity) = userRepository.insertUser(userEntity)

    fun isDataEmpty() = userRepository.isDataEmpty()
}