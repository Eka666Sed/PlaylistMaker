package com.example.playlistmaker.app.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.databinding.ActivitySettingsBinding

object ThemeDataSource {
    var darkTheme = false
    private const val KEY_PREF = "pref"
    private const val KEY_VALUE = "key_value"

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
    fun checkTheme(binding: ActivitySettingsBinding){
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> binding.themeSwither.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> binding.themeSwither.isChecked = false
        }
    }
}