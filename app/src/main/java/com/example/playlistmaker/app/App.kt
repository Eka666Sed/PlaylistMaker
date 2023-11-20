package com.example.playlistmaker.app

import android.app.Application
import com.example.playlistmaker.app.utils.ThemeDataSource


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val themeDatasource = ThemeDataSource
        val darkTheme: Boolean
        darkTheme = themeDatasource.checkedSwitchTheme(this)
        ThemeDataSource.switchTheme(darkTheme)
    }
}