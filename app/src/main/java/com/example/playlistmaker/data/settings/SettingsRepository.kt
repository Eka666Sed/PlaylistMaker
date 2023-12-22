package com.example.playlistmaker.data.settings

import com.example.playlistmaker.domain.settings.model.ApplicationTheme

interface SettingsRepository {
    fun getApplicationTheme(): ApplicationTheme
    fun updateApplicationTheme(applicationTheme: ApplicationTheme)
}