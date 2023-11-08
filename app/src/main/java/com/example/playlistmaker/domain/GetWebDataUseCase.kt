package com.example.playlistmaker.domain

import android.content.Context
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import com.example.playlistmaker.util.ObjectCollection.web

object GetWebDataUseCase {
    fun getWebRequest(binding: ActivitySearchBinding, context: Context) {
        web.getWebRequest(binding, context)
    }

    fun showHistoryRequest(trackAdapter: TrackHistoryAdapter, list: MutableList<Track>) {
        web.showHistoryRequest(trackAdapter, list)
    }
}