package com.example.playlistmaker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.data.entities.Track

private const val PREF = "pref_data"

class SharedPreferencesDataSource(val context: Context) {


    fun saveDataTrack(prefData: SharedPreferences, listTrack: MutableList<Track>, mainKey: String) {
        prefData.edit()
            .putString(mainKey, SharedPreferenceConverter.createJsonFromTracksList(listTrack))
            .apply()
    }

    fun getSharedPreferences(): SharedPreferences {
        val prefData: SharedPreferences = context.getSharedPreferences(
            PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return prefData
    }

    fun clearSharedPreference() {
        getSharedPreferences().edit().clear().apply()
    }
}