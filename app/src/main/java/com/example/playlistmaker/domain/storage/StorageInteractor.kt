package com.example.playlistmaker.domain.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.domain.models.Track

interface StorageInteractor {
    fun saveDataTrack(context: Context, listTrack: MutableList<Track>, mainKey: String)

    fun getSharedPreferences(context: Context): SharedPreferences

    fun clearSharedPreference(context: Context)
}