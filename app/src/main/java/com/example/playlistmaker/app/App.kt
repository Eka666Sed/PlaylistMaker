package com.example.playlistmaker.app

import android.app.Application
import com.example.playlistmaker.creator.SettingsCreator

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SettingsCreator.createInteractor(this)
    }
}