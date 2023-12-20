package com.example.playlistmaker.creator

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesCreator {
    private const val KEY_SHARED_PREFERENCES = "shared_preferences"

    fun createSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(KEY_SHARED_PREFERENCES, Application.MODE_PRIVATE)
}