package com.example.playlistmaker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.helpers.ViewActionsWorkPlace

object SharedPreferenceWorkPlace {
    private const val PREF = "pref_data"

    fun saveDataTrack(prefData: SharedPreferences, listTrack: MutableList<Track>, mainKey: String) {
        prefData.edit()
            .putString(mainKey, SharedPreferenceConverter.createJsonFromTracksList(listTrack))
            .apply()
    }

    fun getSharedPreferences(context: Context): SharedPreferences {
        val prefData: SharedPreferences = context.getSharedPreferences(
            PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return prefData
    }

    fun clearSharedPreference(
        binding: ActivitySearchBinding,
        trackAdapter: TrackHistoryAdapter,
        list: MutableList<Track>,
        context: Context
    ) {
        binding.btnClearHistory.setOnClickListener {
            getSharedPreferences(context).edit().clear().apply()
            trackAdapter.clearListAdapter()
            list.clear()
            ViewActionsWorkPlace.showButtonClear(false, binding, context, trackAdapter)
        }
    }
}