package com.example.playlistmaker.domain

import android.content.SharedPreferences
import com.example.playlistmaker.data.SharedPreferencesDataSource
import com.example.playlistmaker.data.entities.Track

class GetSharedPreferenceActionUseCase(val useCase: SharedPreferencesDataSource) {

    fun saveDataInSharedPreference(
        prefData: SharedPreferences,
        listTrack: MutableList<Track>,
        mainKey: String
    ) {
        useCase.saveDataTrack(prefData, listTrack,mainKey)
    }

    fun getSharedPreferences(): SharedPreferences {
        return useCase.getSharedPreferences()
    }

    fun clearSharedPreference() {
        useCase.clearSharedPreference()
    }
}