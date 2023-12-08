package com.example.playlistmaker.data.setting

import android.app.Application
import com.example.playlistmaker.data.setting.ThemeDataSource


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val themeDatasource = ThemeDataSource
        val darkTheme: Boolean
        darkTheme = themeDatasource.checkedSwitchTheme(this)
        ThemeDataSource.switchTheme(darkTheme)
    }
}