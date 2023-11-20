package com.example.playlistmaker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.data.utils.local_storage.SharedPreferenceConverter
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.storage.StorageRepository

class StorageRepositoryImpl : StorageRepository {

    override fun saveDataTrack(context: Context, listTrack: MutableList<Track>, mainKey: String) {
        getSharedPreferences(context).edit()
            .putString(mainKey, SharedPreferenceConverter.createJsonFromTracksList(listTrack))
            .apply()
    }

    override fun getSharedPreferences(context: Context): SharedPreferences {
        val prefData: SharedPreferences = context.getSharedPreferences(
            PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return prefData
    }

    override fun clearSharedPreference(context: Context) {
        getSharedPreferences(context).edit().clear().apply()
    }

    companion object{
        private const val PREF = "pref_data"
    }
}