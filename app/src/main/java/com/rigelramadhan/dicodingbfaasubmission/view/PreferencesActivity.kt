package com.rigelramadhan.dicodingbfaasubmission.view

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rigelramadhan.dicodingbfaasubmission.data.local.datastore.SettingPreferences
import com.rigelramadhan.dicodingbfaasubmission.databinding.ActivityPreferencesBinding
import com.rigelramadhan.dicodingbfaasubmission.viewmodel.PreferencesViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class PreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "Preferences"
        actionBar?.setDisplayShowHomeEnabled(true)

        val preferences = SettingPreferences.getInstance(dataStore)

        val preferencesViewModel = ViewModelProvider(
            this,
            PreferencesViewModel.PreferencesViewModelFactory(preferences)
        )[PreferencesViewModel::class.java]

        preferencesViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDarkMode.isChecked = false
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            preferencesViewModel.setThemeSetting(isChecked)
        }
    }
}