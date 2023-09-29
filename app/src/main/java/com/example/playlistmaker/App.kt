package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate


private const val KEY_PREF = "pref"
private const val KEY_VALUE = "key_value"

class App : Application() {
    private var darkTheme = false


    override fun onCreate() {
        super.onCreate()
        darkTheme = checkedSwitchTheme()
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES

            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    private fun checkedSwitchTheme(): Boolean {
        return creatingPref().getBoolean(KEY_VALUE, false)
    }

    private fun creatingPref(): SharedPreferences {
        val pref = applicationContext.getSharedPreferences(KEY_PREF, MODE_PRIVATE)
        return pref
    }
    fun saveData(check:Boolean){
        val editor = creatingPref().edit()
        editor.putBoolean(KEY_VALUE, darkTheme)
        editor.apply()
    }
}