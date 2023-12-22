package com.example.playlistmaker.domain.settings.impl

import com.example.playlistmaker.data.settings.SettingsRepository
import com.example.playlistmaker.domain.settings.SettingsInteractor
import com.example.playlistmaker.domain.settings.model.ApplicationTheme

class SettingsInteractorImpl(
    private val settingsRepository: SettingsRepository
) : SettingsInteractor {

    override fun getApplicationTheme(): ApplicationTheme = settingsRepository.getApplicationTheme()

    override fun updateApplicationTheme(applicationTheme: ApplicationTheme) {
        settingsRepository.updateApplicationTheme(applicationTheme)
    }
}