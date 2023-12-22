package com.example.playlistmaker.data.settings.impl

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.data.settings.SettingsRepository
import com.example.playlistmaker.domain.settings.model.ApplicationTheme

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    companion object {
        private const val KEY_APPLICATION_THEME = "key_value"
    }

    init {
        updateApplicationTheme(getApplicationTheme())
    }

    override fun getApplicationTheme(): ApplicationTheme {
        val themeName = sharedPreferences.getString(
            KEY_APPLICATION_THEME, ApplicationTheme.LIGHT.name
        ) ?: ApplicationTheme.LIGHT.name
        return ApplicationTheme.valueOf(themeName)
    }

    override fun updateApplicationTheme(applicationTheme: ApplicationTheme) {
        val nightMode = when (applicationTheme) {
            ApplicationTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            ApplicationTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
        saveApplicationTheme(applicationTheme)
    }

    private fun saveApplicationTheme(applicationTheme: ApplicationTheme) {
        with(sharedPreferences.edit()) {
            putString(KEY_APPLICATION_THEME, applicationTheme.name)
            apply()
        }
    }
}