package com.example.playlistmaker.data.setting.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.domain.settings.model.StorageInteractor
import com.example.playlistmaker.domain.settings.model.StorageRepository

class StorageInteractorImpl(private val storage: StorageRepository) : StorageInteractor {

    override fun saveDataTrack(context: Context, listTrack: MutableList<Track>, mainKey: String) {
        storage.saveDataTrack(context, listTrack, mainKey)
    }

    override fun getSharedPreferences(context: Context): SharedPreferences {
        return getSharedPreferences(context)
    }

    override fun clearSharedPreference(context: Context) {
        storage.clearSharedPreference(context)
    }
}