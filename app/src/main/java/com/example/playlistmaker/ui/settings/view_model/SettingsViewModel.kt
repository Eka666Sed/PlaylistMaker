package com.example.playlistmaker.ui.settings.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.playlistmaker.creator.SettingsCreator
import com.example.playlistmaker.creator.SharingCreator
import com.example.playlistmaker.domain.settings.SettingsInteractor
import com.example.playlistmaker.domain.settings.model.ApplicationTheme
import com.example.playlistmaker.domain.sharing.SharingInteractor

class SettingsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val sharingInteractor: SharingInteractor = SharingCreator.createInteractor(application)
    private val settingsInteractor: SettingsInteractor =
        SettingsCreator.createInteractor(application)

    private val _applicationTheme =
        MutableLiveData<ApplicationTheme>(settingsInteractor.getApplicationTheme())
    val applicationTheme: LiveData<Boolean> = _applicationTheme.map { it == ApplicationTheme.DARK }

    fun onShareAppButtonClicked() = sharingInteractor.shareApp()

    fun onWriteSupportButtonClicked() = sharingInteractor.openSupport()

    fun onUserAgreementsButtonClicked() = sharingInteractor.openUserAgreement()

    fun onThemeSwitchClicked(isChecked: Boolean) {
        val applicationTheme = if (isChecked) ApplicationTheme.DARK else ApplicationTheme.LIGHT
        settingsInteractor.updateApplicationTheme(applicationTheme)
    }
}