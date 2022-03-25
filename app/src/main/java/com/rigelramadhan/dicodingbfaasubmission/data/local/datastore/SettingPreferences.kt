package com.rigelramadhan.dicodingbfaasubmission.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val THEME_KEY = booleanPreferencesKey("theme_settings")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var instance: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return instance ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                this.instance = instance
                instance
            }
        }
    }
}