package com.example.playlistmaker.domain.settings.model

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.data.player.Track

interface StorageInteractor {
    fun saveDataTrack(context: Context, listTrack: MutableList<Track>, mainKey: String)

    fun getSharedPreferences(context: Context): SharedPreferences

    fun clearSharedPreference(context: Context)
}