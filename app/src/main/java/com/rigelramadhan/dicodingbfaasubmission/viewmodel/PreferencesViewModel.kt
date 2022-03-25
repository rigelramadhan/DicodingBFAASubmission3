package com.rigelramadhan.dicodingbfaasubmission.viewmodel

import androidx.lifecycle.*
import com.rigelramadhan.dicodingbfaasubmission.data.local.datastore.SettingPreferences
import kotlinx.coroutines.launch

class PreferencesViewModel(private val preferences: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    fun setThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkMode)
        }
    }

    class PreferencesViewModelFactory(private val preferences: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
                return PreferencesViewModel(preferences) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}