package com.example.playlistmaker.domain.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.data.StorageRepositoryImpl
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.storage.StorageInteractor

class StorageInteractorImpl(private val storage: StorageRepositoryImpl) : StorageInteractor {

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