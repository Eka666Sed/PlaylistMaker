package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.data.settings.SettingsRepository
import com.example.playlistmaker.data.settings.impl.SettingsRepositoryImpl
import com.example.playlistmaker.domain.settings.SettingsInteractor
import com.example.playlistmaker.domain.settings.impl.SettingsInteractorImpl

object SettingsCreator {

    private var settingsInteractor: SettingsInteractorImpl? = null
    private var settingsRepository: SettingsRepository? = null

    fun createInteractor(context: Context): SettingsInteractor {
        val settingsRepository = createSettingsRepository(context)
        return settingsInteractor ?: SettingsInteractorImpl(settingsRepository).apply {
            settingsInteractor = this
        }
    }

    private fun createSettingsRepository(context: Context): SettingsRepository {
        val sharedPreferences = SharedPreferencesCreator.createSharedPreferences(context)
        return settingsRepository ?: SettingsRepositoryImpl(sharedPreferences).apply {
            settingsRepository = this
        }
    }
}