package com.example.playlistmaker.domain.settings

import com.example.playlistmaker.domain.settings.model.ApplicationTheme

interface SettingsInteractor {
    fun getApplicationTheme(): ApplicationTheme
    fun updateApplicationTheme(applicationTheme: ApplicationTheme)
}