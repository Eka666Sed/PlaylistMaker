package com.example.playlistmaker

import android.content.SharedPreferences

object SharedPreferenceWorkPlace {
    private val sharedPreferencesConverter = SharedPreferenceConverter

    fun saveDataTrack(prefData: SharedPreferences, listTrack: MutableList<Track>, mainKey:String) {
        prefData.edit()
            .putString(mainKey, sharedPreferencesConverter.createJsonFromTracksList(listTrack))
            .apply()
    }
}