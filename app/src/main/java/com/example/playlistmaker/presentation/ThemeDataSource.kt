package com.example.playlistmaker.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

private const val KEY_PREF = "pref"
private const val KEY_VALUE = "key_value"

object ThemeDataSource {
    var darkTheme = false

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

    fun checkedSwitchTheme(context: Context): Boolean {
        return creatingPref(context).getBoolean(KEY_VALUE, false)
    }

    private fun creatingPref(applicationContext: Context): SharedPreferences {
        val pref = applicationContext.getSharedPreferences(KEY_PREF, Application.MODE_PRIVATE)
        return pref
    }
    fun saveData(check:Boolean,context: Context){
        val editor = creatingPref(context).edit()
        editor.putBoolean(KEY_VALUE, darkTheme)
        editor.apply()
    }
}